package com.bartoszdrozd.mediapp.auth.models

import com.bartoszdrozd.mediapp.insurancepicker.models.InsuranceCompany
import com.google.firebase.firestore.DocumentReference

data class UserDetails(
    val firstName: String = "",
    val lastName: String = "",
    val sex: Int = -1,
    val dateOfBirth: Long = 0,
    var age: Int = -1,
    val phoneNumber: String? = null,
    val gp: DocumentReference? = null,
    val insuranceCompany: InsuranceCompany? = null
)
