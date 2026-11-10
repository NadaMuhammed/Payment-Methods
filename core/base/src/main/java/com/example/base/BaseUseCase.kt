package com.example.base

abstract class BaseUseCase<T, V> {

    abstract suspend fun invoke(input: T): BaseResult<V>
}