package com.bartoszdrozd.mediapp.healthforms.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bartoszdrozd.mediapp.healthforms.dtos.AlzheimersFormDTO
import com.bartoszdrozd.mediapp.healthforms.models.AlzheimersFormField
import com.bartoszdrozd.mediapp.healthforms.models.FormErrorCode
import com.bartoszdrozd.mediapp.healthforms.usecases.ISaveAlzheimersFormUseCase
import com.bartoszdrozd.mediapp.utils.Error
import com.bartoszdrozd.mediapp.utils.Success
import com.bartoszdrozd.mediapp.utils.succeeded
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlzheimersFormViewModel @Inject constructor(
    private val saveFormUseCase: ISaveAlzheimersFormUseCase
) : ViewModel() {
    private val _validationErrors =
        MutableLiveData<List<Pair<AlzheimersFormField, FormErrorCode>>>()
    private val _generalError: MutableLiveData<FormErrorCode> = MutableLiveData()
    private val saveSuccessChannel = Channel<Int>(Channel.BUFFERED)

    val validationErrors: LiveData<List<Pair<AlzheimersFormField, FormErrorCode>>> =
        _validationErrors
    val generalError: LiveData<FormErrorCode> = _generalError
    val saveSuccess = saveSuccessChannel.receiveAsFlow()

    private fun validateForm(form: AlzheimersFormDTO): Boolean {
        val errors = mutableListOf<Pair<AlzheimersFormField, FormErrorCode>>()

        if (form.educationLevel == -1) {
            errors.add(AlzheimersFormField.EDUCATION to FormErrorCode.REQUIRED_FIELD)
        }
        if (form.miniMentalState == null) {
            errors.add(AlzheimersFormField.MINI_MENTAL_STATE to FormErrorCode.REQUIRED_FIELD)
        }
        if (form.clinicalDementiaRating == null) {
            errors.add(AlzheimersFormField.CLINICAL_RATING to FormErrorCode.REQUIRED_FIELD)
        }
        if (form.socioEconomicStatus == -1) {
            errors.add(AlzheimersFormField.SOCIOECONOMIC_STATUS to FormErrorCode.REQUIRED_FIELD)
        }
        if (form.estTotalIntracranial == null) {
            errors.add(AlzheimersFormField.EST_TOTAL_INTRACRANIAL to FormErrorCode.REQUIRED_FIELD)
        }
        if (form.normalizeWholeBrain == null) {
            errors.add(AlzheimersFormField.NORMALIZE_WHOLE_BRAIN to FormErrorCode.REQUIRED_FIELD)
        }

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    fun saveForm(form: AlzheimersFormDTO) {
        if (validateForm(form)) {
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
}