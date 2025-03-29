package com.vasberc.presentation.utils

interface MediaSessionListener {
    fun onMusicStarted()
    fun onFinish()
    fun onSongChanged()
}