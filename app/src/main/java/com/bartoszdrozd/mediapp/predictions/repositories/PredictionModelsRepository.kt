package com.bartoszdrozd.mediapp.predictions.repositories

import com.bartoszdrozd.mediapp.utils.Result

class PredictionModelsRepository : IPredictionModelsRepository {
    override suspend fun getHeart(): Result<Unit, Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun getAlzheimers(): Result<Unit, Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun getDiabetes(): Result<Unit, Unit> {
        TODO("Not yet implemented")
    }
}