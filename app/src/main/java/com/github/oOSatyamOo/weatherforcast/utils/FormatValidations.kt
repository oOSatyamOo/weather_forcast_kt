package com.github.oOSatyamOo.weatherforcast.utils

fun String.onlyLettersAndSpaces(): String {
    return filter { it.isLetter() || it.isWhitespace() }
}

fun String.onlyDigits(): String {
    return filter { it.isDigit() }
}