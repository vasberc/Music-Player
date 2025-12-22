package com.vasberc.presentation.componets.addlistdialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vasberc.domain.model.DomainResult
import com.vasberc.domain.usecase.AddListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class AddListViewModel(
    private val addListUseCase: AddListUseCase
) : ViewModel() {

    // backing MutableStateFlow and public read-only StateFlow
    private val _result = MutableStateFlow<DomainResult<Unit>?>(null)
    val result = _result.asStateFlow()

    private val _textInput = MutableStateFlow("")
    val textInput = _textInput.asStateFlow()

    fun resetResult() {
        _result.value = null
        _textInput.value = ""
    }

    fun addList() {
        viewModelScope.launch {
            _result.value = addListUseCase(textInput.value)
        }
    }

    fun onValueChange(newValue: String) {
        _textInput.update { newValue }
    }

    fun onError(errorMessage: String?) {
        if (errorMessage != null) {
            _result.value = DomainResult.Error(errorMessage)
        } else {
            _result.value = null
        }
    }
}