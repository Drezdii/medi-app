package com.bartoszdrozd.mediapp.predictions.dtos

import java.time.LocalDateTime
import java.time.ZoneOffset

data class PredictionDTO(
    val value: Float = 0f,
    val formId: String = "",
    val date: Long = LocalDateTime.now(ZoneOffset.UTC).atZone(ZoneOffset.UTC).toEpochSecond(),
    val id: String = ""
)