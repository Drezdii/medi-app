package com.bartoszdrozd.mediapp.predictions.dtos

import com.bartoszdrozd.mediapp.predictions.models.PredictionType
import java.time.LocalDateTime
import java.time.ZoneOffset

data class PredictionDTO(
    val predictionType: PredictionType = PredictionType.HEART,
    val value: Float = 0f,
    val formId: String = "",
    val date: Long = LocalDateTime.now(ZoneOffset.UTC).atZone(ZoneOffset.UTC).toEpochSecond(),
    val id: String = ""
)