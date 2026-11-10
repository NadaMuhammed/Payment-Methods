package com.example.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<
        STATE : BaseContract.State,
        SIDE_EFFECT : BaseContract.SideEffect,
        EVENT : BaseContract.Event>(
    initialState: STATE
) : ViewModel() {

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<STATE> = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<SIDE_EFFECT>(replay = 1)
    val sideEffect: SharedFlow<SIDE_EFFECT> = _sideEffect.asSharedFlow()

    fun updateState(newState: (STATE) -> STATE) {
        if (newState(state.value) != state.value) {
            _state.value = newState(state.value)
        }
    }

    fun setSideEffect(newSideEffect: SIDE_EFFECT) {
        viewModelScope.launch {
            _sideEffect.emit(newSideEffect)
        }
    }

    abstract fun setEvent(newEvent: EVENT)

    fun <T> executeUseCase(
        useCase: suspend () -> BaseResult<T>,
        onLoading: ((Boolean) -> Unit)? = null,
        onSuccess: (T?) -> Unit,
        onError: (() -> Unit)? = null
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            onLoading?.invoke(true)

            when (val result = useCase.invoke()) {
                is BaseResult.BaseSuccess -> {
                    onSuccess.invoke(result.data)

                    onLoading?.invoke(false)
                }

                else -> {
                    onError?.invoke()

                    onLoading?.invoke(false)
                }
            }
        }
    }
}