package com.bartoszdrozd.mediapp.userprofile.usecases

import com.bartoszdrozd.mediapp.auth.repositories.IUsersRepository
import com.bartoszdrozd.mediapp.predictions.dtos.PredictionDTO
import com.bartoszdrozd.mediapp.predictions.repositories.IPredictionsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUsersLatestPredictionUseCase @Inject constructor(
    private val usersRepo: IUsersRepository,
    private val repo: IPredictionsRepository
) :
    IGetUsersLatestPredictionUseCase {
    @ExperimentalCoroutinesApi
    override suspend fun invoke(): Flow<PredictionDTO?> {
        val uid = usersRepo.getCurrentUser()?.uuid ?: throw Exception()

        return repo.getLatestPrediction(uid)
    }
}