package com.bartoszdrozd.mediapp.auth.models

data class UserDetails(
    val firstName: String = "",
    val lastName: String = "",
    val sex: Int = -1,
    val dateOfBirth: Long = 0,
    var age: Int = -1,
    val phoneNumber: String? = null,
    val gpId: String? = null,
    val insuranceCompanyId: String? = null
)
