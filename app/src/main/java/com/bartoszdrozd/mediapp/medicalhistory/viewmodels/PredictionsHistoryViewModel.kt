package com.bartoszdrozd.mediapp.medicalhistory.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bartoszdrozd.mediapp.medicalhistory.usecases.IGetPredictionsHistoryUseCase
import com.bartoszdrozd.mediapp.predictions.dtos.PredictionDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class PredictionsHistoryViewModel @Inject constructor(
    private val getPredictionsHistoryUseCase: IGetPredictionsHistoryUseCase
) : ViewModel() {
    private val _predictions: MutableLiveData<List<PredictionDTO>> = MutableLiveData()
    val predictions: LiveData<List<PredictionDTO>> = _predictions

    init {
        viewModelScope.launch {
            getPredictionsHistoryUseCase.execute().collect {
                _predictions.value = it
            }
        }
    }
}