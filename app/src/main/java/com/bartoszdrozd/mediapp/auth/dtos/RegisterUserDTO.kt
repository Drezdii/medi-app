package com.bartoszdrozd.mediapp.auth.dtos

data class RegisterUserDTO(
    var email: String = "",
    var password: String = "",
    var firstName: String = "",
    var lastName: String = "",
    var sex: Int = 0,
    var dateOfBirth: Long? = null,
    var phoneNumber: String? = null
)