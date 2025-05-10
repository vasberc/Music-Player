package com.vasberc.presentation.screens.home.allfolders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vasberc.domain.model.FolderModel
import com.vasberc.domain.usecase.GetAllMusicFilesUseCase
import com.vasberc.domain.usecase.RefreshAllMusicFilesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class HomeViewModel(
    getAllMusicFilesUseCase: GetAllMusicFilesUseCase,
    private val refreshAllMusicFilesUseCase: RefreshAllMusicFilesUseCase
): ViewModel() {
    private val _files = getAllMusicFilesUseCase()
    val files = _files.stateIn(
        scope = viewModelScope,
        initialValue = null,
        started = SharingStarted.Lazily
    )

    fun refreshFolders() {
        viewModelScope.launch {
            refreshAllMusicFilesUseCase()
        }
    }
}