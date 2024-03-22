# Explore Marks
**Explore Marks** - это мобильное приложение, разработанное для профильного задания в RTUITLab ВУЗа РТУ МИРЭА.

## Описание
Кругу путешественников, которые посещают различные места по всему миру, захотелось отмечать на картах точки, где они побывали и делиться этими метками друг с другом. 

Данное приложение может помочь путешественникам легче общаться и оценивать изысканность посещенных мест.

Приложение делит функционал на доступный гостям и функционал доступный только зарегистрированным пользователям.

## Скриншоты
<img src="https://github.com/Towich/ExploreMarks/assets/100920758/261ee4f5-5169-455e-a40d-fe14492f1f88" width="190">
<img src="https://github.com/Towich/ExploreMarks/assets/100920758/8fbe39f6-5138-47ee-a2de-e34e07fc3a66" width="190">
<img src="https://github.com/Towich/ExploreMarks/assets/100920758/b28cdc30-5467-472e-921f-3e6e90c393cc" width="190">
<img src="https://github.com/Towich/ExploreMarks/assets/100920758/06e022dc-adf2-4136-a633-aed0b56492ed" width="190">

# Функционал (гость)
* Просмотр меток на картах/списком
* Создание метки на карте как гость (без указания авторства)
* Регистрация
* Вход зарегистрированного пользователя без ввода логина и пароля

# Функционал (зарегистрированный пользователь)
* Весь функционал доступный гостю
* Поставить/Убрать лайк у метки
* Создание метки на карте с указанием авторства
* Удаление созданной метки
* Обеспечение работу всех функций приложения без подключения к интернету на основе полученных ранее данных

## Используемые технологии
* Language: Kotlin
* UI: Jetpack Compose
* Map: Yandex.Map (lite)
* Architecture: MVVM + Use Cases
* Async: Kotlin Coroutines
* DI: Hilt
* HTTP Client: KTor
* Image loading library: Coil
