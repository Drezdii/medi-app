package com.bartoszdrozd.mediapp.predictions.dtos

import com.bartoszdrozd.mediapp.utils.DiseaseType
import java.time.LocalDateTime
import java.time.ZoneOffset

data class PredictionDTO(
    val predictionType: DiseaseType = DiseaseType.HEART,
    val value: Float = 0f,
    val formId: String = "",
    val date: Long = LocalDateTime.now(ZoneOffset.UTC).atZone(ZoneOffset.UTC).toEpochSecond(),
    val id: String = ""
)