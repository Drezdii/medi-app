package com.bartoszdrozd.mediapp.medicalhistory.usecases

import com.bartoszdrozd.mediapp.predictions.dtos.PredictionDTO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

interface IGetPredictionsHistoryUseCase {
    @ExperimentalCoroutinesApi
    suspend fun execute(): Flow<List<PredictionDTO>>
}