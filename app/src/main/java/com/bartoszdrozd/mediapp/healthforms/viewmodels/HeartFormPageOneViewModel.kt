package com.bartoszdrozd.mediapp.healthforms.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bartoszdrozd.mediapp.healthforms.dtos.HeartFormDTO
import com.bartoszdrozd.mediapp.healthforms.models.health.FormErrorCode
import com.bartoszdrozd.mediapp.healthforms.models.health.HeartFormField
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HeartFormPageOneViewModel @Inject constructor() : ViewModel() {
    private val _validationErrors =
        MutableLiveData<List<Pair<HeartFormField, FormErrorCode>>>()

    val validationErrors: LiveData<List<Pair<HeartFormField, FormErrorCode>>> =
        _validationErrors

    fun validateForm(form: HeartFormDTO): Boolean {
        val errors = mutableListOf<Pair<HeartFormField, FormErrorCode>>()
        if (form.serumCholesterol == null) {
            errors.add(HeartFormField.SERUM_CHOLESTEROL to FormErrorCode.REQUIRED_FIELD)
        }
        if (form.fastingBloodSugar == null) {
            errors.add(HeartFormField.FASTING_BLOOD_SUGAR to FormErrorCode.REQUIRED_FIELD)
        }
        if (form.restingECG == -1) {
            errors.add(HeartFormField.RESTING_ECG to FormErrorCode.REQUIRED_FIELD)
        }
        if (form.restingBloodPressure == null) {
            errors.add(HeartFormField.RESTING_BLOOD_PRESSURE to FormErrorCode.REQUIRED_FIELD)
        }
        if (form.maxHR == null) {
            errors.add(HeartFormField.MAXIMUM_HR to FormErrorCode.REQUIRED_FIELD)
        }
        if (form.stDepression == null) {
            errors.add(HeartFormField.ST_DEPRESSION to FormErrorCode.REQUIRED_FIELD)
        }

        _validationErrors.value = errors
        return errors.isEmpty()
    }
}