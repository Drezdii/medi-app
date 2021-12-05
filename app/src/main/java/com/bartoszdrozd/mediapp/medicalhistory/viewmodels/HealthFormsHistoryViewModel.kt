package com.bartoszdrozd.mediapp.medicalhistory.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bartoszdrozd.mediapp.medicalhistory.dtos.HealthFormEntryDTO
import com.bartoszdrozd.mediapp.utils.DiseaseType

class HealthFormsHistoryViewModel : ViewModel() {
    private val _forms: MutableLiveData<List<HealthFormEntryDTO>> = MutableLiveData()
    val forms: LiveData<List<HealthFormEntryDTO>> get() = _forms

    init {
        _forms.value = listOf(
            HealthFormEntryDTO(DiseaseType.DIABETES, "abc11", 1638664468),
            HealthFormEntryDTO(DiseaseType.DIABETES, "abc12", 1638664468),
            HealthFormEntryDTO(DiseaseType.DIABETES, "abc13", 1638664468),
            HealthFormEntryDTO(DiseaseType.ALZHEIMERS, "abc14", 1638664468),
            HealthFormEntryDTO(DiseaseType.ALZHEIMERS, "abc15", 1638664468),
            HealthFormEntryDTO(DiseaseType.ALZHEIMERS, "abc16", 1638664468),
            HealthFormEntryDTO(DiseaseType.ALZHEIMERS, "abc17", 1638664468),
            HealthFormEntryDTO(DiseaseType.HEART, "abc18", 1638664468),
            HealthFormEntryDTO(DiseaseType.HEART, "abc19", 1638664468),
            HealthFormEntryDTO(DiseaseType.HEART, "abc21", 1638664468),
            HealthFormEntryDTO(DiseaseType.HEART, "abc22", 1638664468),
        )
    }
}