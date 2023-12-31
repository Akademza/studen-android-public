package com.example.studen_android.screens.base

import com.example.studen_android.base.BaseViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object BaseVM: BaseViewModel() {

    private val _viewState = MutableStateFlow<BaseViewState>(BaseViewState.Load)
    val viewState = _viewState.asStateFlow()

    fun navigateTo(screen: BaseViewState) {
        _viewState.update { screen }
    }

    override fun onCleared() {
        super.onCleared()
        cancel()
    }
}