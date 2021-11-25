package com.bartoszdrozd.mediapp.predictions.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bartoszdrozd.mediapp.predictions.repositories.PredictionModelsRepository
import kotlinx.coroutines.launch

class HeartPredictionViewModel : ViewModel() {
    fun run() {
        val test = PredictionModelsRepository()
        viewModelScope.launch {
            test.getHeart()
        }
    }
}