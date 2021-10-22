package com.bartoszdrozd.mediapp.auth.dtos

data class RegisterFormPageOneDTO(
    val email: String,
    val confirmEmail: String,
    val password: String,
    val confirmPassword: String
)
