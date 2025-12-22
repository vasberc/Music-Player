package com.vasberc.presentation.screens.home.folder

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.vasberc.domain.usecase.GetFilesOfFolderUseCase
import com.vasberc.presentation.navigation.homenavigation.HomeRoute
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import org.koin.android.annotation.KoinViewModel
import timber.log.Timber

@KoinViewModel
class FolderViewModel(
    getFilesOfFolderUseCase: GetFilesOfFolderUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    val folderPath = savedStateHandle.toRoute<HomeRoute.Folder>().folderPath
    private val _folder = getFilesOfFolderUseCase(folderPath)
    val folder = _folder.onStart {
        Timber.d("Viewmodel onStart FolderViewModel Started ${this@FolderViewModel}")
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = null
    )
}