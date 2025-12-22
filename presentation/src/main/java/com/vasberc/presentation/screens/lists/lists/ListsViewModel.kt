package com.vasberc.presentation.screens.lists.lists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vasberc.domain.usecase.GetListsUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import org.koin.android.annotation.KoinViewModel
import timber.log.Timber

@KoinViewModel
class ListsViewModel(
    val getListsUseCase: GetListsUseCase
): ViewModel() {
    private val _lists = getListsUseCase()
    val lists = _lists.onStart {
        Timber.d("Viewmodel onStart ListsViewModel Started ${this@ListsViewModel}")
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = null
    )
}