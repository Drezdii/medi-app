package com.bartoszdrozd.mediapp.predictions.viewmodels

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bartoszdrozd.mediapp.auth.repositories.IUsersRepository
import com.bartoszdrozd.mediapp.predictions.models.Prediction
import com.bartoszdrozd.mediapp.utils.DiseaseType
import com.bartoszdrozd.mediapp.predictions.usecases.IGetPredictionUseCase
import com.bartoszdrozd.mediapp.utils.Error
import com.bartoszdrozd.mediapp.utils.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PredictionViewModel @Inject constructor(
    private val userRepo: IUsersRepository,
    private val getPredictionUseCase: IGetPredictionUseCase
) :
    ViewModel() {
    private val _prediction: MutableLiveData<Prediction> = MutableLiveData()
    val prediction: LiveData<Prediction> = _prediction

    @SuppressLint("NullSafeMutableLiveData")
    fun predict(type: DiseaseType) {
        viewModelScope.launch {
            val res = getPredictionUseCase.execute(type)

            if (res is Success) {
                _prediction.value = res.value
            } else {
                Log.d("TEST", (res as Error).reason.toString())
            }
        }
    }
}