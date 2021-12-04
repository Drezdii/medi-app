package com.bartoszdrozd.mediapp.predictions.usecases

import com.bartoszdrozd.mediapp.predictions.models.Prediction
import com.bartoszdrozd.mediapp.predictions.models.PredictionType
import com.bartoszdrozd.mediapp.utils.Result

interface IGetPredictionUseCase {
    suspend fun execute(predictionType: PredictionType): Result<Prediction, Unit>
}