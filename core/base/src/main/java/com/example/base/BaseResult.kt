package com.example.base

sealed class BaseResult<out T> {

    data class BaseSuccess<out T>(
        val data: T? = null
    ): BaseResult<T>()

    data object BaseFailure: BaseResult<Nothing>()
}