package com.vasberc.presentation.screens.lists.lists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vasberc.domain.usecase.GetListsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class ListsViewModel(
    val getListsUseCase: GetListsUseCase
): ViewModel() {
    private val _lists = getListsUseCase()
    val lists = _lists.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = null
    )
}