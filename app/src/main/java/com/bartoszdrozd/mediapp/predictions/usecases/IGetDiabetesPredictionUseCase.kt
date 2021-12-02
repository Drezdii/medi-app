package com.bartoszdrozd.mediapp.predictions.usecases

import com.bartoszdrozd.mediapp.healthforms.dtos.DiabetesFormDTO
import com.bartoszdrozd.mediapp.predictions.models.Prediction
import com.bartoszdrozd.mediapp.utils.Result

interface IGetDiabetesPredictionUseCase {
    suspend fun execute(form: DiabetesFormDTO): Result<Prediction, Unit>
}