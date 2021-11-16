package com.bartoszdrozd.mediapp.healthforms.dtos

data class HeartFormDTO(
    var restingBloodPressure: Int? = -1,
    var serumCholesterol: Int? = -1,
    var fastingBloodSugar: Int? = -1,
    var restingECG: Int? = -1,
    var maxHR: Int? = -1,
    var stDepression: Int? = -1,
    var chestPainType: Int = 0,
    var exerciseInducedAngina: Int = 0,
    var peakSTSegment: Int = 0,
    var majorVessels: Int = 0,
    var thalassemia: Int = 0,
    var gender: Int = 0,
    var age: Int = 0,
    var date: Long = 0,
    var uid: String = ""
)
