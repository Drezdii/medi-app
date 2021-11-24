package com.bartoszdrozd.mediapp.predictions.repositories

import com.bartoszdrozd.mediapp.utils.Result

interface IPredictionModelsRepository {
    suspend fun getHeart(): Result<Unit, Unit>
    suspend fun getAlzheimers(): Result<Unit, Unit>
    suspend fun getDiabetes(): Result<Unit, Unit>
}