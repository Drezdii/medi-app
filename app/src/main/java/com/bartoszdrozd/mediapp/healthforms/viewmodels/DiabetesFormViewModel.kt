package com.bartoszdrozd.mediapp.healthforms.viewmodels

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bartoszdrozd.mediapp.auth.repositories.IUsersRepository
import com.bartoszdrozd.mediapp.healthforms.dtos.DiabetesFormDTO
import com.bartoszdrozd.mediapp.healthforms.models.FormErrorCode
import com.bartoszdrozd.mediapp.healthforms.usecases.ISaveDiabetesFormUseCase
import com.bartoszdrozd.mediapp.utils.Error
import com.bartoszdrozd.mediapp.utils.succeeded
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiabetesFormViewModel @Inject constructor(
    private val repository: IUsersRepository,
    private val saveFormUseCase: ISaveDiabetesFormUseCase
) :
    ViewModel() {
    private val _isPregnanciesFieldVisible = MutableLiveData(false)
    private val _pregnanciesError = MutableLiveData<FormErrorCode>()
    private val _glucoseError = MutableLiveData<FormErrorCode>()
    private val _insulinError = MutableLiveData<FormErrorCode>()
    private val _bloodPressureError = MutableLiveData<FormErrorCode>()
    private val _skinThicknessError = MutableLiveData<FormErrorCode>()
    private val _bmiError = MutableLiveData<FormErrorCode>()
    private val _pedigreeFuncError: MutableLiveData<FormErrorCode> = MutableLiveData()
    private val _generalError: MutableLiveData<FormErrorCode> = MutableLiveData()
    private val saveSuccessChannel = Channel<Int>(Channel.BUFFERED)

    private val isFormValid: Boolean
        get() {
            return _pregnanciesError.value == null && _glucoseError.value == null &&
                    insulinError.value == null && _bloodPressureError.value == null &&
                    _skinThicknessError.value == null && _bmiError.value == null
        }

    val isPregnanciesFieldVisible: LiveData<Boolean> = _isPregnanciesFieldVisible
    val pregnanciesError: LiveData<FormErrorCode> = _pregnanciesError
    val glucoseError: LiveData<FormErrorCode> = _glucoseError
    val insulinError: LiveData<FormErrorCode> = _insulinError
    val bloodPressureError: LiveData<FormErrorCode> = _bloodPressureError
    val skinThicknessError: LiveData<FormErrorCode> = _skinThicknessError
    val bmiError: LiveData<FormErrorCode> = _bmiError
    val pedigreeFuncError: LiveData<FormErrorCode> = _bmiError
    val generalError: LiveData<FormErrorCode> = _generalError
    val saveSuccess = saveSuccessChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            val user = repository.getCurrentUser()
            _isPregnanciesFieldVisible.value = user?.details?.sex != 0
        }
    }

    @SuppressLint("NullSafeMutableLiveData")
    fun saveForm(form: DiabetesFormDTO) {
        validatePregnancies(form.pregnancies)
        validateGlucose(form.glucoseLevel)
        validateInsulin(form.insulinLevel)
        validateBloodPressure(form.bloodPressureLevel)
        validateSkinThickness(form.skinThickness)
        validateBmi(form.bmi)
        validatePedigreeFunc(form.pedigreeFunc)

        if (isFormValid) {
            viewModelScope.launch {
                val res = saveFormUseCase.execute(form)
                if (res.succeeded) {
                    saveSuccessChannel.send(1)
                } else {
                    _generalError.value = (res as Error).reason
                }
            }
        }
    }

    private fun validatePregnancies(count: Int?) {
        _pregnanciesError.value =
            if (count == null && isPregnanciesFieldVisible.value == true) FormErrorCode.REQUIRED_FIELD else null
    }

    private fun validateGlucose(level: Float?) {
        _glucoseError.value = if (level == null) FormErrorCode.REQUIRED_FIELD else null
    }

    private fun validateInsulin(level: Float?) {
        _insulinError.value = if (level == null) FormErrorCode.REQUIRED_FIELD else null
    }

    private fun validateBloodPressure(level: Float?) {
        _bloodPressureError.value = if (level == null) FormErrorCode.REQUIRED_FIELD else null
    }

    private fun validateSkinThickness(measurement: Float?) {
        _skinThicknessError.value = if (measurement == null) FormErrorCode.REQUIRED_FIELD else null
    }

    private fun validateBmi(value: Float?) {
        _bmiError.value = if (value == null) FormErrorCode.REQUIRED_FIELD else null
    }

    private fun validatePedigreeFunc(value: Float?) {
        _pedigreeFuncError.value = if (value == null) FormErrorCode.REQUIRED_FIELD else null
    }
}