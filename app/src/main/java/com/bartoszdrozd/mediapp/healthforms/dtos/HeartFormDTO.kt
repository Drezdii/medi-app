package com.bartoszdrozd.mediapp.healthforms.dtos

data class HeartFormDTO(
    var restingBloodPressure: Float? = -1f,
    var serumCholesterol: Float? = -1f,
    var fastingBloodSugar: Float? = -1f,
    var restingECG: Int = -1,
    var maxHR: Float? = -1f,
    var stDepression: Float? = -1f,
    var chestPainType: Int = 0,
    var exerciseInducedAngina: Int = 0,
    var peakSTSegment: Int = 0,
    var majorVessels: Int = 0,
    var thalassemia: Int = 0,
    var gender: Int = 0,
    var age: Int = 0,
    var date: Long = 0,
    var uid: String = "",
    var id: String = ""
)
