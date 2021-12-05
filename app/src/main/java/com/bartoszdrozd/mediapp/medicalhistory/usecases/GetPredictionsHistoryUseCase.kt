package com.bartoszdrozd.mediapp.medicalhistory.usecases

import com.bartoszdrozd.mediapp.auth.repositories.IUsersRepository
import com.bartoszdrozd.mediapp.predictions.dtos.PredictionDTO
import com.bartoszdrozd.mediapp.predictions.repositories.IPredictionsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPredictionsHistoryUseCase @Inject constructor(
    private val usersRepo: IUsersRepository,
    private val repo: IPredictionsRepository
) :
    IGetPredictionsHistoryUseCase {
    @ExperimentalCoroutinesApi
    override suspend fun execute(): Flow<List<PredictionDTO>> {
        val uuid = usersRepo.getCurrentUser()?.uuid ?: throw Exception()

        return repo.getAll(uuid)
    }
}