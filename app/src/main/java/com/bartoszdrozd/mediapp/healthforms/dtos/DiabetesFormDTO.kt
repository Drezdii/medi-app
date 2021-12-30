package com.bartoszdrozd.mediapp.healthforms.dtos

import java.time.LocalDateTime
import java.time.ZoneOffset

data class DiabetesFormDTO(
    val pregnancies: Int? = -1,
    val glucoseLevel: Float? = -1f,
    val insulinLevel: Float? = -1f,
    val bloodPressureLevel: Float? = -1f,
    val skinThickness: Float? = -1f,
    val bmi: Float? = -1f,
    val pedigreeFunc: Float? = -1f,
    var age: Int = -1,
    var date: Long = LocalDateTime.now(ZoneOffset.UTC).atZone(ZoneOffset.UTC).toEpochSecond(),
    var uid: String = "",
    var id: String = ""
)
