package com.bartoszdrozd.mediapp.healthforms.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bartoszdrozd.mediapp.healthforms.dtos.HeartFormDTO
import com.bartoszdrozd.mediapp.healthforms.models.FormErrorCode
import com.bartoszdrozd.mediapp.healthforms.usecases.ISaveHeartFormUseCase
import com.bartoszdrozd.mediapp.utils.Error
import com.bartoszdrozd.mediapp.utils.succeeded
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneOffset
import javax.inject.Inject

@HiltViewModel
class HeartFormViewModel @Inject constructor(private val saveFormUseCase: ISaveHeartFormUseCase) :
    ViewModel() {
    private val _formState = HeartFormDTO()
    private val _generalError: MutableLiveData<FormErrorCode> = MutableLiveData()
    private val saveSuccessChannel = Channel<Int>(Channel.BUFFERED)

    val generalError: LiveData<FormErrorCode> = _generalError
    val saveSuccess = saveSuccessChannel.receiveAsFlow()

    fun saveFirstPage(form: HeartFormDTO) {
        _formState.apply {
            serumCholesterol = form.serumCholesterol
            fastingBloodSugar = form.fastingBloodSugar
            restingECG = form.restingECG
            restingBloodPressure = form.restingBloodPressure
            maxHR = form.maxHR
            stDepression = form.stDepression
        }
    }

    fun saveForm(form: HeartFormDTO) {
        _formState.apply {
            chestPainType = form.chestPainType
            exerciseInducedAngina = form.exerciseInducedAngina
            peakSTSegment = form.peakSTSegment
            majorVessels = form.majorVessels
            thalassemia = form.thalassemia
            // Add the Date field when saving the form
            date = LocalDateTime.now(ZoneOffset.UTC).atZone(ZoneOffset.UTC).toEpochSecond()
        }

        viewModelScope.launch {
            val res = saveFormUseCase.execute(_formState)
            if (res.succeeded) {
                saveSuccessChannel.send(1)
            } else {
                _generalError.value = (res as Error).reason
            }
        }
    }
}