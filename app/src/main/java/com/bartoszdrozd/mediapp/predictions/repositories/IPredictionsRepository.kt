package com.bartoszdrozd.mediapp.predictions.repositories

import com.bartoszdrozd.mediapp.predictions.dtos.PredictionDTO
import com.bartoszdrozd.mediapp.utils.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

interface IPredictionsRepository {
    suspend fun save(uuid: String, prediction: PredictionDTO): Result<Unit, Unit>

    @ExperimentalCoroutinesApi
    suspend fun getAll(uuid: String): Flow<List<PredictionDTO>>
}