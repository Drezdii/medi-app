package com.bartoszdrozd.mediapp.healthforms.dtos

import java.time.LocalDateTime
import java.time.ZoneOffset

data class DiabetesFormDTO(
    val pregnancies: Int? = -1,
    val glucoseLevel: Int? = -1,
    val insulinLevel: Int? = -1,
    val bloodPressureLevel: Int? = -1,
    val skinThickness: Int? = -1,
    val bmi: Int? = -1,
    var age: Int = -1,
    var date: Long = LocalDateTime.now(ZoneOffset.UTC).atZone(ZoneOffset.UTC).toEpochSecond(),
    var uid: String = ""
)
