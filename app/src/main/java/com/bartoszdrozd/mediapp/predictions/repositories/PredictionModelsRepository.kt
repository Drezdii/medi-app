package com.bartoszdrozd.mediapp.predictions.repositories

import com.bartoszdrozd.mediapp.utils.Error
import com.bartoszdrozd.mediapp.utils.Result
import com.bartoszdrozd.mediapp.utils.Success
import com.google.firebase.ml.modeldownloader.*
import kotlinx.coroutines.tasks.await

class PredictionModelsRepository : IPredictionModelsRepository {
    private val conditions = CustomModelDownloadConditions.Builder()
        .requireWifi()  // Also possible: .requireCharging() and .requireDeviceIdle()
        .build()

    override suspend fun getHeart(): Result<CustomModel, Unit> {
        return try {
            val model = FirebaseModelDownloader.getInstance()
                .getModel("heart-v1", DownloadType.LOCAL_MODEL_UPDATE_IN_BACKGROUND, conditions).await()
            Success(model)
        } catch (ex: FirebaseMlException) {
            Error(Unit)
        }
    }

    override suspend fun getAlzheimers(): Result<CustomModel, Unit> {
        return try {
            val model = FirebaseModelDownloader.getInstance()
                .getModel("alzheimers-v1", DownloadType.LOCAL_MODEL_UPDATE_IN_BACKGROUND, conditions).await()
            Success(model)
        } catch (e: FirebaseMlException) {
            Error(Unit)
        }
    }

    override suspend fun getDiabetes(): Result<CustomModel, Unit> {
        return try {
            val model = FirebaseModelDownloader.getInstance()
                .getModel("diabetes-v1", DownloadType.LOCAL_MODEL_UPDATE_IN_BACKGROUND, conditions).await()
            Success(model)
        } catch (e: FirebaseMlException) {
            Error(Unit)
        }
    }
}