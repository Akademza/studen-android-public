package com.example.studen_android.base

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel:  ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext =
        SupervisorJob() + Dispatchers.Default + CoroutineName(this::class.java.simpleName) + CoroutineExceptionHandler { coroutineContext, throwable ->
            handleException(throwable = throwable)
        }

    protected fun handleException(throwable: Throwable) {
        Log.d("exceptions", throwable.toString())
    }
}