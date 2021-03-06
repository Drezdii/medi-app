package com.bartoszdrozd.mediapp.predictions.usecases

import com.bartoszdrozd.mediapp.predictions.models.Prediction
import com.bartoszdrozd.mediapp.utils.DiseaseType
import com.bartoszdrozd.mediapp.utils.Result

interface IGetPredictionUseCase {
    suspend fun execute(predictionType: DiseaseType): Result<Prediction, Unit>
}