package com.bartoszdrozd.mediapp.forms.dtos

import java.time.LocalDateTime
import java.time.ZoneOffset

data class DiabetesFormDTO(
    val pregnancies: Int?,
    val glucoseLevel: Int?,
    val insulinLevel: Int?,
    val bloodPressureLevel: Int?,
    val skinThickness: Int?,
    val bmi: Int?,
    var age: Int = 0,
    var date: Long = LocalDateTime.now(ZoneOffset.UTC).atZone(ZoneOffset.UTC).toEpochSecond(),
    var uid: String = ""
)
