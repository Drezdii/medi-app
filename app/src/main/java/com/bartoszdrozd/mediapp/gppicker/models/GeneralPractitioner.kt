package com.bartoszdrozd.mediapp.gppicker.models

data class GeneralPractitioner(
    val firstName: String = "",
    val lastName: String = "",
    val dateOfBirth: Long? = 0,
    val email: String = "",
    val medicalCenter: String = "",
    val phoneNumber: String = "",
    // Medical council number
    val mcn: Int = 0,
    val picture: String? = "",
    val uid: String = ""
)
