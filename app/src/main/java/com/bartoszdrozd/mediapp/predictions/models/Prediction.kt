package com.bartoszdrozd.mediapp.predictions.models

data class Prediction(
    val uid: String = "",
    val value: Float,
    val date: Long = 0
    // Form that the prediction was based on
)
