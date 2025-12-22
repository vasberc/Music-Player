package com.vasberc.presentation.screens.lists.list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.vasberc.domain.usecase.GetFilesOfListUseCase
import com.vasberc.presentation.navigation.listsnavigation.ListsRoute
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import org.koin.android.annotation.KoinViewModel
import timber.log.Timber

@KoinViewModel
class SelectedListViewModel(
    getFilesOfListUseCase: GetFilesOfListUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    val listName = savedStateHandle.toRoute<ListsRoute.List>().listName
    private val _folder = getFilesOfListUseCase(listName)
    val folder = _folder.onStart {
        Timber.d("Viewmodel onStart SelectedListViewModel Started ${this@SelectedListViewModel}")
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = null
    )
}