package com.example.studen_android.screens.load

import com.apphud.sdk.Apphud
import com.example.studen_android.BuildConfig
import com.example.studen_android.base.BaseViewModel
import com.example.studen_android.data.repositories.MainRepository
import com.example.studen_android.screens.base.BaseVM
import com.example.studen_android.screens.base.BaseViewState
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class LoadViewModel: BaseViewModel() {


    private val _messages = MutableSharedFlow<String>()
    val messages = _messages.asSharedFlow()

    private val _viewState = MutableStateFlow<LoadViewState>(LoadViewState.Loading)
    val viewState = _viewState.asStateFlow()


    init {
        auth()
    }

    fun auth() = launch {
        val uuid = Apphud.userId() ?: return@launch
        val playerID = ""
        _viewState.emit(LoadViewState.Loading)
        MainRepository.auth(uuid = uuid, playerID = playerID, isModal = false)
            .onFailure { _viewState.emit(LoadViewState.Error(it.localizedMessage.orEmpty())) }
            .onSuccess {
                _viewState.emit(LoadViewState.Success)
                checkVersionAndNavigate(it.use_api, it.version_check, it.version)
            }
    }

    private fun checkVersionAndNavigate(useApi: Boolean, checkVersion: Boolean, latestVersion: String) {
        val currentAppVersion = BuildConfig.VERSION_NAME

        if (!checkVersion && currentAppVersion != latestVersion) {
            BaseVM.navigateTo(BaseViewState.Native)
        } else if (!checkVersion && currentAppVersion == latestVersion) {
            BaseVM.navigateTo(BaseViewState.WebView)
        } else if (checkVersion && currentAppVersion != latestVersion) {
            BaseVM.navigateTo(BaseViewState.Update)
        } else if (checkVersion && currentAppVersion == latestVersion) {
            BaseVM.navigateTo(BaseViewState.WebView)
        } else {
            _viewState.update{ LoadViewState.Error("Try again") }
        }
    }

    override fun onCleared() {
        super.onCleared()
        cancel()
    }
}