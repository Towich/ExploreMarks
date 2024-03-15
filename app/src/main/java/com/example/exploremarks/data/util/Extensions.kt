package com.example.exploremarks.data.util

fun Double.format(digits: Int) = "%.${digits}f".format(this).replace(',', '.')