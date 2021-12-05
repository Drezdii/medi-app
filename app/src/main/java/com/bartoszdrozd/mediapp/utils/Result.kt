package com.bartoszdrozd.mediapp.utils

sealed class Result<out Success, out Error>

data class Success<out Data>(val value: Data) : Result<Data, Nothing>()
data class Error<out Error>(val reason: Error) : Result<Nothing, Error>()

val Result<*, *>.succeeded
    get() = this is Success