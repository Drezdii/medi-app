package com.bartoszdrozd.mediapp.predictions.usecases

import com.bartoszdrozd.mediapp.predictions.models.Prediction
import com.bartoszdrozd.mediapp.utils.Result

interface IGetHeartDiseasePredictionUseCase {
    suspend fun execute(uuid: String): Result<Prediction, Unit>
}