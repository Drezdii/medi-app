package com.bartoszdrozd.mediapp.medicalhistory.dtos

import com.bartoszdrozd.mediapp.utils.DiseaseType

data class HealthFormEntryDTO(
    val diseaseType: DiseaseType = DiseaseType.HEART,
    val id: String = "",
    val date: Long = 0
)