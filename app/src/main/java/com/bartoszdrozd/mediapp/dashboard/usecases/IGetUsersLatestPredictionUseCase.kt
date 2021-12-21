package com.bartoszdrozd.mediapp.dashboard.usecases

import com.bartoszdrozd.mediapp.predictions.dtos.PredictionDTO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

interface IGetUsersLatestPredictionUseCase {
    @ExperimentalCoroutinesApi
    suspend operator fun invoke(): Flow<PredictionDTO?>
}
