package com.bartoszdrozd.mediapp.forms.dtos

data class DiabetesFormDTO(
    val pregnancies: Int?,
    val glucoseLevel: Int?,
    val insulinLevel: Int?,
    val bloodPressureLevel: Int?,
    val skinThickness: Int?,
    val bmi: Int?,
    var age: Int = 0
)
