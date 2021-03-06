package com.bartoszdrozd.mediapp.healthforms.dtos

import java.time.LocalDateTime
import java.time.ZoneOffset

data class AlzheimersFormDTO(
    var gender: Int = 0,
    val dominantHand: Int = 0,
    val educationLevel: Int? = -1,
    val socioEconomicStatus: Int? = -1,
    val miniMentalState: Float? = -1f,
    val clinicalDementiaRating: Float? = -1f,
    val estTotalIntracranial: Float? = -1f,
    val normalizeWholeBrain: Float? = 1f,
    var date: Long = LocalDateTime.now(ZoneOffset.UTC).atZone(ZoneOffset.UTC).toEpochSecond(),
    var age: Int = 0,
    var uid: String = "",
    val id: String = ""
)