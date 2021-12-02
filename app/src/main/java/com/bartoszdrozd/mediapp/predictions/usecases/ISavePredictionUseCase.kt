package com.bartoszdrozd.mediapp.predictions.usecases

import com.bartoszdrozd.mediapp.predictions.dtos.PredictionDTO
import com.bartoszdrozd.mediapp.utils.Result

interface ISavePredictionUseCase {
    suspend fun execute(uuid: String, prediction: PredictionDTO): Result<Unit, Unit>
}