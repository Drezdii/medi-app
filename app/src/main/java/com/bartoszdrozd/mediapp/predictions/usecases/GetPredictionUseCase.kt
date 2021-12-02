package com.bartoszdrozd.mediapp.predictions.usecases

import com.bartoszdrozd.mediapp.auth.repositories.IUsersRepository
import com.bartoszdrozd.mediapp.healthforms.repositories.IHealthFormsRepository
import com.bartoszdrozd.mediapp.predictions.models.Prediction
import com.bartoszdrozd.mediapp.predictions.models.PredictionType
import com.bartoszdrozd.mediapp.predictions.models.PredictionType.*
import com.bartoszdrozd.mediapp.utils.Error
import com.bartoszdrozd.mediapp.utils.Result
import com.bartoszdrozd.mediapp.utils.Success
import javax.inject.Inject

class GetPredictionUseCase @Inject constructor(
    private val userRepo: IUsersRepository,
    private val healthFormRepo: IHealthFormsRepository,
    private val heartPredictionUseCase: IGetHeartDiseasePredictionUseCase,
    private val diabetesPredictionUseCase: IGetDiabetesPredictionUseCase,
    private val alzheimersPredictionUseCase: IGetAlzheimersPredictionUseCase
) :
    IGetPredictionUseCase {
    override suspend fun execute(type: PredictionType): Result<Prediction, Unit> {
        val uuid = userRepo.getCurrentUser()?.uuid ?: return Error(Unit)

        when (type) {
            HEART -> {
                // Get the latest heart disease form for this user
                val res = healthFormRepo.getLatestHeartForm(uuid)

                val form = if (res is Success) {
                    res.value
                } else {
                    return Error(Unit)
                }

                return if (form != null) {
                    heartPredictionUseCase.execute(form)
                } else {
                    // Inform user that no form was available to make a prediction
                    Error(Unit)
                }
            }
            ALZHEIMERS -> {
                // Get the latest heart disease form for this user
                val res = healthFormRepo.getLatestAlzheimers(uuid)

                val form = if (res is Success) {
                    res.value
                } else {
                    return Error(Unit)
                }

                return if (form != null) {
                    alzheimersPredictionUseCase.execute(form)
                } else {
                    // Inform user that no form was available to make a prediction
                    Error(Unit)
                }
            }
            DIABETES -> {
                // Get the latest heart disease form for this user
                val res = healthFormRepo.getLatestDiabetes(uuid)

                val form = if (res is Success) {
                    res.value
                } else {
                    return Error(Unit)
                }

                return if (form != null) {
                    diabetesPredictionUseCase.execute(form)
                } else {
                    // Inform user that no form was available to make a prediction
                    Error(Unit)
                }
            }
        }
    }
}