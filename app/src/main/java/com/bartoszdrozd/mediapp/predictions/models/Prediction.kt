package com.bartoszdrozd.mediapp.predictions.models

data class Prediction(
    val uid: String,
    val value: Float,
    val type: PredictionType,
    val date: Long
    // Form that the prediction was based on
)
