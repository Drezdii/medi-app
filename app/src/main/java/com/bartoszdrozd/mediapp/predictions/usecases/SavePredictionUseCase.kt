package com.bartoszdrozd.mediapp.predictions.usecases

import com.bartoszdrozd.mediapp.predictions.dtos.PredictionDTO
import com.bartoszdrozd.mediapp.predictions.repositories.IPredictionsRepository
import com.bartoszdrozd.mediapp.utils.Result
import javax.inject.Inject

class SavePredictionUseCase @Inject constructor(private val repo: IPredictionsRepository) : ISavePredictionUseCase {
    override suspend fun execute(uuid: String, prediction: PredictionDTO): Result<Unit, Unit> {
        return repo.save(uuid, prediction)
    }
}