package com.bartoszdrozd.mediapp.predictions.usecases

import com.bartoszdrozd.mediapp.predictions.models.Prediction
import com.bartoszdrozd.mediapp.utils.Result

class GetHeartDiseasePredictionUseCase : IGetHeartDiseasePredictionUseCase {
    override suspend fun execute(): Result<Prediction, Unit> {
        TODO("Not yet implemented")
    }
}