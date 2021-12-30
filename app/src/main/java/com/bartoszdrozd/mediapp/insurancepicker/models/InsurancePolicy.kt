package com.bartoszdrozd.mediapp.insurancepicker.models

data class InsurancePolicy(
    val uid: String = "",
    val insuranceCompanyId: String = "",
    val lengthMonths: Int = 0,
    val name: String = "",
    val description: String = "",
    val price: Float = 0f
)
