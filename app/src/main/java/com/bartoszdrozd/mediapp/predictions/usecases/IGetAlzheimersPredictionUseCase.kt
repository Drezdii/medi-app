package com.bartoszdrozd.mediapp.predictions.usecases

import com.bartoszdrozd.mediapp.healthforms.dtos.AlzheimersFormDTO
import com.bartoszdrozd.mediapp.predictions.models.Prediction
import com.bartoszdrozd.mediapp.utils.Result

interface IGetAlzheimersPredictionUseCase {
    suspend fun execute(form: AlzheimersFormDTO): Result<Prediction, Unit>
}