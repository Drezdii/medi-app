package com.bartoszdrozd.mediapp.healthforms.repositories

import android.util.Log
import com.bartoszdrozd.mediapp.healthforms.dtos.AlzheimersFormDTO
import com.bartoszdrozd.mediapp.healthforms.dtos.DiabetesFormDTO
import com.bartoszdrozd.mediapp.healthforms.dtos.HeartFormDTO
import com.bartoszdrozd.mediapp.healthforms.models.health.FormErrorCode
import com.bartoszdrozd.mediapp.utils.Error
import com.bartoszdrozd.mediapp.utils.Result
import com.bartoszdrozd.mediapp.utils.Success
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.tasks.await

class HealthFormsRepository : IHealthFormsRepository {
    override suspend fun saveDiabetes(form: DiabetesFormDTO): Result<Unit, FormErrorCode> {
        return try {
            val diabetesData = hashMapOf(
                "uid" to form.uid,
                "age" to form.age,
                "date" to form.date,
                "pregnancies" to (form.pregnancies ?: 0),
                "glucoseLevel" to form.glucoseLevel,
                "insulinLevel" to form.insulinLevel,
                "bloodPressure" to form.bloodPressureLevel,
                "skinThickness" to form.skinThickness,
                "bmi" to form.bmi
            )

            FirebaseFirestore.getInstance().collection("diabetes").add(diabetesData).await()
            Success(Unit)
        } catch (e: FirebaseFirestoreException) {
            Error(FormErrorCode.GENERIC_ERROR)
        }
    }

    override suspend fun saveAlzheimers(form: AlzheimersFormDTO): Result<Unit, FormErrorCode> {
        return try {
            val data = hashMapOf(
                "uid" to form.uid,
                "age" to form.age,
                "date" to form.date,
                "gender" to form.gender,
                "dominantHand" to form.dominantHand,
                "miniMentalState" to form.miniMentalState,
                "clinicalDementiaRating" to form.clinicalDementiaRating,
                "socioEconomicStatus" to form.socioEconomicStatus,
                "estTotalIntracranial" to form.estTotalIntracranial,
                "normalizeWholeBrain" to form.normalizeWholeBrain
            )

            FirebaseFirestore.getInstance().collection("alzheimers").add(data).await()

            Success(Unit)
        } catch (e: FirebaseFirestoreException) {
            Error(FormErrorCode.GENERIC_ERROR)
        }
    }

    override suspend fun saveHeart(form: HeartFormDTO): Result<Unit, FormErrorCode> {
        return try {
            Log.d("TEST", form.majorVessels.toString() + "XD")
            val data = hashMapOf(
                "uid" to form.uid,
                "age" to form.age,
                "date" to form.date,
                "gender" to form.gender,
                "chestPainType" to form.chestPainType,
                "restingBloodPressure" to form.restingBloodPressure,
                "serumCholesterol" to form.serumCholesterol,
                "fastingBloodSugar" to form.fastingBloodSugar,
                "restingECG" to form.restingECG,
                "maxHR" to form.maxHR,
                "exerciseInducedAngina" to form.exerciseInducedAngina,
                "stDepression" to form.stDepression,
                "peakSTSegment" to form.peakSTSegment,
                "majorVessels" to form.majorVessels,
                "thalassemia" to form.thalassemia,
            )
            FirebaseFirestore.getInstance().collection("heart").add(data).await()
            Success(Unit)
        } catch (e: FirebaseFirestoreException) {
            Error(FormErrorCode.GENERIC_ERROR)
        }
    }
}