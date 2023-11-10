package com.example.studen_android.helpers

import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

fun LocalDateTime.format(): String {
    val outputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale("ru","RU"))
    return outputFormat.format(this)
}

fun Date.format(): String {
    val outputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale("ru","RU"))
    return outputFormat.format(this)
}