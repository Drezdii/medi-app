package com.bartoszdrozd.mediapp.userprofile.usecases

import com.bartoszdrozd.mediapp.auth.repositories.IUsersRepository
import com.bartoszdrozd.mediapp.predictions.repositories.IPredictionsRepository
import com.bartoszdrozd.mediapp.utils.DiseaseType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetOverallHealthScoreUseCase @Inject constructor(
    private val repo: IPredictionsRepository,
    private val usersRepo: IUsersRepository
) :
    IGetOverallHealthScoreUseCase {
    @ExperimentalCoroutinesApi
    override suspend fun execute(): Flow<Float> {
        val uid = usersRepo.getCurrentUser()!!.uuid

        val latestHeart = repo.getLatestPrediction(uid, DiseaseType.HEART)
        val latestAlzheimers = repo.getLatestPrediction(uid, DiseaseType.ALZHEIMERS)
        val latestDiabetes = repo.getLatestPrediction(uid, DiseaseType.DIABETES)

        return combine(latestHeart, latestAlzheimers, latestDiabetes) { h, a, d ->
            val heartValue: Float? = h?.value
            val alzheimersValue: Float? = a?.value
            val diabetesValue: Float? = d?.value

            val numbers = mutableListOf<Float>()

            if (heartValue != null) {
                numbers.add(heartValue)
            }

            if (alzheimersValue != null) {
                numbers.add(alzheimersValue)
            }

            if (diabetesValue != null) {
                numbers.add(diabetesValue)
            }

            numbers.average().toFloat()
        }
    }
}