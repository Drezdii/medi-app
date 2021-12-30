package com.bartoszdrozd.mediapp.predictions.usecases

import com.bartoszdrozd.mediapp.predictions.dtos.PredictionDTO
import com.bartoszdrozd.mediapp.predictions.repositories.IPredictionsRepository
import com.bartoszdrozd.mediapp.utils.Result
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@DelicateCoroutinesApi
class SavePredictionUseCase @Inject constructor(private val repo: IPredictionsRepository) :
    ISavePredictionUseCase {
    override suspend fun execute(uuid: String, prediction: PredictionDTO): Result<Unit, Unit> {
        // Fire and forget the call to save the prediction to blockchain
        GlobalScope.launch(Dispatchers.IO) {
            repo.saveToBlockchain(uuid, prediction)
        }
        return repo.save(uuid, prediction)
    }
}