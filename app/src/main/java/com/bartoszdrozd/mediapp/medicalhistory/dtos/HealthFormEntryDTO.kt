package com.bartoszdrozd.mediapp.medicalhistory.dtos

import com.bartoszdrozd.mediapp.utils.DiseaseType

data class HealthFormEntryDTO(
    val diseaseType: DiseaseType,
    val id: String,
    val date: Long
)