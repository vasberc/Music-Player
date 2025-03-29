package com.vasberc.presentation.utils

import java.util.Locale

fun Long.toDurationString(): String {
    val totalSeconds = this / 1000
    val seconds = String.format(Locale.getDefault(), "%02d", totalSeconds % 60)
    val totalMinutes = totalSeconds / 60
    val minutes = String.format(Locale.getDefault(), "%02d:", totalMinutes % 60)
    val hours = (totalMinutes / 60).takeUnless { it == 0L }?.toString(2)?.plus(":") ?: ""
    return "$hours$minutes$seconds"
}

fun Long.toFileSize(): String {
    return ""
}