package com.example.base

abstract class BaseMapper<INPUT, OUTPUT> {

    abstract fun map(input: INPUT): OUTPUT
}