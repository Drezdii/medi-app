package com.bartoszdrozd.mediapp.predictions.repositories

import com.bartoszdrozd.mediapp.utils.Result
import com.google.firebase.ml.modeldownloader.CustomModel

interface IPredictionModelsRepository {
    suspend fun getHeart(): Result<CustomModel?, Unit>
    suspend fun getAlzheimers(): Result<CustomModel?, Unit>
    suspend fun getDiabetes(): Result<CustomModel?, Unit>
}