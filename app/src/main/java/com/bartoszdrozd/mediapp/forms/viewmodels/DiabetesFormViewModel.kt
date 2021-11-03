package com.bartoszdrozd.mediapp.forms.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bartoszdrozd.mediapp.auth.repositories.IUsersRepository
import com.bartoszdrozd.mediapp.forms.dtos.DiabetesFormDTO
import com.bartoszdrozd.mediapp.forms.models.health.DiabetesFormField
import com.bartoszdrozd.mediapp.forms.models.health.FormError
import com.bartoszdrozd.mediapp.forms.models.health.FormErrorCode
import com.bartoszdrozd.mediapp.forms.usecases.ISaveDiabetesFormUseCase
import com.bartoszdrozd.mediapp.utils.Error
import com.bartoszdrozd.mediapp.utils.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiabetesFormViewModel @Inject constructor(
    private val repository: IUsersRepository,
    private val saveFormUseCase: ISaveDiabetesFormUseCase
) :
    ViewModel() {
    private val _isPregnanciesFieldVisible = MutableLiveData(false)
    private val _formErrors = MutableLiveData<List<FormError>>(listOf())

    val isPregnanciesFieldVisible: LiveData<Boolean> = _isPregnanciesFieldVisible
    val formErrors: LiveData<List<FormError>> = _formErrors

    init {
        viewModelScope.launch {
            val user = repository.getCurrentUser()
            _isPregnanciesFieldVisible.value = user?.details?.sex != 0
        }
    }

    fun saveForm(form: DiabetesFormDTO) {
        // Clear any previous errors
        _formErrors.value = listOf()

        validatePregnancies(form.pregnancies)
        validateGlucose(form.glucoseLevel)
        validateInsulin(form.insulinLevel)
        validateBloodPressure(form.bloodPressureLevel)
        validateSkinThickness(form.skinThickness)
        validateBmi(form.bmi)

        if (_formErrors.value!!.isEmpty()) {
            viewModelScope.launch {
                val res = saveFormUseCase.execute(form)
                if (res is Success) {
                    // Navigate
                } else {
                    addValidationError(DiabetesFormField.ERROR_BOX, (res as Error).reason)
                }
            }
        }
    }

    private fun addValidationError(field: DiabetesFormField, error: FormErrorCode) {
        _formErrors.value = _formErrors.value!!.plus(FormError(field, error))
    }

    private fun validatePregnancies(count: Int?) {
        if (count == null && isPregnanciesFieldVisible.value!!) {
            addValidationError(DiabetesFormField.PREGNANCIES, FormErrorCode.REQUIRED_FIELD)
        }
    }

    private fun validateGlucose(level: Int?) {
        if (level == null) {
            addValidationError(DiabetesFormField.GLUCOSE_LEVELS, FormErrorCode.REQUIRED_FIELD)
        }
    }

    private fun validateInsulin(level: Int?) {
        if (level == null) {
            addValidationError(DiabetesFormField.INSULIN_LEVELS, FormErrorCode.REQUIRED_FIELD)
        }
    }

    private fun validateBloodPressure(level: Int?) {
        if (level == null) {
            addValidationError(DiabetesFormField.BLOOD_PRESSURE, FormErrorCode.REQUIRED_FIELD)
        }
    }

    private fun validateSkinThickness(measurement: Int?) {
        if (measurement == null) {
            addValidationError(DiabetesFormField.SKIN_THICKNESS, FormErrorCode.REQUIRED_FIELD)
        }
    }

    private fun validateBmi(value: Int?) {
        if (value == null) {
            addValidationError(DiabetesFormField.BMI, FormErrorCode.REQUIRED_FIELD)
        }
    }
}