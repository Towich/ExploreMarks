package com.example.exploremarks.ui.screen.map

import android.net.Uri
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exploremarks.data.model.MarkUIModel
import com.example.exploremarks.data.model.UserData
import com.example.exploremarks.domain.CreateMarkUseCase
import com.example.exploremarks.domain.DeleteMarkUseCase
import com.example.exploremarks.domain.DislikeMarkUseCase
import com.example.exploremarks.domain.GetImageBitmapByUriUseCase
import com.example.exploremarks.domain.GetMarksUseCase
import com.example.exploremarks.domain.GetUserDataUseCase
import com.example.exploremarks.domain.LikeMarkUseCase
import com.example.exploremarks.network.util.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val getMarks: GetMarksUseCase,
    private val getUserData: GetUserDataUseCase,
    private val likeMark: LikeMarkUseCase,
    private val dislikeMark: DislikeMarkUseCase,
    private val createMark: CreateMarkUseCase,
    private val deleteMark: DeleteMarkUseCase,
    private val getImageBitmapByUri: GetImageBitmapByUriUseCase
) : ViewModel() {

    private val _screenUiState = MutableStateFlow<MapScreenUiState>(MapScreenUiState.Initial)
    val screenUiState: StateFlow<MapScreenUiState> = _screenUiState

    private val _listOfMarks = MutableStateFlow<MutableList<MarkUIModel>?>(null)
    val listOfMarks: StateFlow<List<MarkUIModel>?> = _listOfMarks

    private val _markUiState = MutableStateFlow<MarkUiState>(MarkUiState.Initial)
    val markUiState: StateFlow<MarkUiState> = _markUiState

    private val _newMarkUiState = MutableStateFlow<MarkUiState>(MarkUiState.Initial)
    val newMarkUiState: StateFlow<MarkUiState> = _newMarkUiState

    private val _deleteMarkUiState = MutableStateFlow<MarkUiState>(MarkUiState.Initial)
    val deleteMarkUiState: StateFlow<MarkUiState> = _deleteMarkUiState

    val userData: UserData = getUserData()

    init {
        performGetMarks()
    }

    private fun performGetMarks() {
        viewModelScope.launch {
            _screenUiState.value = MapScreenUiState.Loading

            when (val result = getMarks()) {
                is ApiResult.Success -> {
                    _screenUiState.value = MapScreenUiState.Success
                    _listOfMarks.value = result.data.toMutableList()
                }

                is ApiResult.Error -> {
                    _screenUiState.value = MapScreenUiState.Error(result.error)
                }

                else -> {

                }
            }
        }
    }

    fun getUserId(): UUID {
        return userData.userId
    }

    fun performLikeMark(markId: UUID) {
        viewModelScope.launch {
            _markUiState.value = MarkUiState.Loading

            when (val result = likeMark(markId = markId)) {
                is ApiResult.Success -> {
                    _markUiState.value = MarkUiState.Success(result.data)
                    _listOfMarks.value?.map {
                        if (it.id == markId) {
                            it.isLiked = result.data.isLiked
                            it.likes = result.data.likes
                        }
                    }

                }

                is ApiResult.Error -> {
                    _markUiState.value = MarkUiState.Error(result.error)
                }

                else -> {

                }
            }
        }
    }

    fun performDislikeMark(markId: UUID) {
        viewModelScope.launch {
            _markUiState.value = MarkUiState.Loading

            when (val result = dislikeMark(markId = markId)) {
                is ApiResult.Success -> {
                    _markUiState.value = MarkUiState.Success(result.data)
                    _listOfMarks.value?.map {
                        if (it.id == markId) {
                            it.isLiked = false
                            it.likes--
                        }
                    }
                }

                is ApiResult.Error -> {
                    _markUiState.value = MarkUiState.Error(result.error)
                }

                else -> {

                }
            }
        }
    }

    fun performCreateMark(newMarkUIModel: MarkUIModel, imageMark: ImageBitmap?){
        viewModelScope.launch {
            _newMarkUiState.value = MarkUiState.Loading

            when (val result = createMark(newMark = newMarkUIModel, imageMark = imageMark)) {
                is ApiResult.Success -> {
                    _listOfMarks.value?.add(result.data)
                    _newMarkUiState.value = MarkUiState.Success(result.data)
                }

                is ApiResult.Error -> {
                    _newMarkUiState.value = MarkUiState.Error(result.error)
                }

                else -> {

                }
            }
        }
    }

    fun performDeleteMark(markId: UUID){
        viewModelScope.launch {
            _deleteMarkUiState.value = MarkUiState.Loading

            when (val result = deleteMark(markId = markId)) {
                is ApiResult.Success -> {
                    var indexToRemove = 0
                    for(mark in _listOfMarks.value!!){
                        if(mark.id == markId){
                            break
                        }
                        else{
                            indexToRemove++
                        }
                    }

                    _deleteMarkUiState.value = MarkUiState.Success(indexToRemove)
                    _listOfMarks.value?.removeAt(indexToRemove)
                }

                is ApiResult.Error -> {
                    _deleteMarkUiState.value = MarkUiState.Error(result.error)
                }

                else -> {

                }
            }
        }
    }

    fun changeNewMarkUIState(newState: MarkUiState){
        _newMarkUiState.value = newState
    }

    fun changeDeleteMarkUIState(newState: MarkUiState){
        _deleteMarkUiState.value = newState
    }

    fun performGetImageBitmapByUri(uri: Uri): ImageBitmap {
        return getImageBitmapByUri(uri)
    }
}