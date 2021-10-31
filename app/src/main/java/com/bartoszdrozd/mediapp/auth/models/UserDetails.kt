package com.bartoszdrozd.mediapp.auth.models

data class UserDetails(
    val firstName: String = "",
    val lastName: String = "",
    val sex: Int = -1,
    val dateOfBirth: Long = 0,
    val phoneNumber: String? = null
)
