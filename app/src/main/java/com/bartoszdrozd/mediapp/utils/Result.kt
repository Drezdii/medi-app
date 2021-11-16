package com.bartoszdrozd.mediapp.utils

sealed class Result<out Success, out Error>
    data class Success<out Success>(val value: Success) : Result<Success, Nothing>()
    data class Error<out Error>(val reason: Error) : Result<Nothing, Error>()
    data class Loading<out Loading>(val loading: Loading) : Result<Nothing, Nothing>()
