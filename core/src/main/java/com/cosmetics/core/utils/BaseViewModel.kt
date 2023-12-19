package com.cosmetics.core.utils

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cosmetics.data.coroutine.CoroutineContextProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

sealed class ViewState
object Completed : ViewState()
class Failure(val pair: Pair<String, (() -> Unit)?>) : ViewState()
object Loading : ViewState()

abstract class BaseViewModel : ViewModel() {
    abstract fun getBindingId(): Int

    val viewState = MutableLiveData<ViewState>()
    var dismissSnackBar = MutableLiveData(false)
    inline fun ViewModel.launch(
        coroutineContext: CoroutineContext = CoroutineContextProvider().main,
        crossinline block: suspend CoroutineScope.() -> Unit
    ): Job {
        return viewModelScope.launch(coroutineContext) { block() }
    }

    fun executeUseCase(isLoading: Boolean = true, action: suspend () -> Unit) {
        if (isLoading) {
            viewState.value = Loading
        }
        launch {
            action()
        }
    }

    fun handleError(message: String, retryCallback: (() -> Unit)? = null): Failure {
        val state = Failure(Pair(message, retryCallback))
        viewState.value = state
        return state
    }

    fun handleErrorAsync(message: String, retryCallback: (() -> Unit)? = null) {
        viewState.postValue(Failure(Pair(message, retryCallback)))
    }
}