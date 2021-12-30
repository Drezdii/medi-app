package com.bartoszdrozd.mediapp.predictions.repositories

import com.bartoszdrozd.mediapp.predictions.dtos.PredictionDTO
import com.bartoszdrozd.mediapp.utils.DiseaseType
import com.bartoszdrozd.mediapp.utils.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

interface IPredictionsRepository {
    suspend fun save(uid: String, prediction: PredictionDTO): Result<Unit, Unit>
    suspend fun saveToBlockchain(uid: String, prediction: PredictionDTO)

    @ExperimentalCoroutinesApi
    suspend fun getAllByUserId(uid: String): Flow<List<PredictionDTO>>

    @ExperimentalCoroutinesApi
    suspend fun getLatestPrediction(uid: String, predictionType: DiseaseType): Flow<PredictionDTO?>

    @ExperimentalCoroutinesApi
    suspend fun getLatestPrediction(uid: String): Flow<PredictionDTO?>
}