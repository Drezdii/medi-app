package com.bartoszdrozd.mediapp.healthforms.dtos

import java.time.LocalDateTime
import java.time.ZoneOffset

data class AlzheimersFormDTO(
    var gender: Int = 0,
    val dominantHand: Int = 0,
    val educationLevel: Int?,
    val socioEconomicStatus: Int?,
    val miniMentalState: Int?,
    val clinicalDementiaRating: Float?,
    val estTotalIntracranial: Int?,
    val normalizeWholeBrain: Float?,
    var date: Long = LocalDateTime.now(ZoneOffset.UTC).atZone(ZoneOffset.UTC).toEpochSecond(),
    var age: Int = 0,
    var uid: String = ""
)