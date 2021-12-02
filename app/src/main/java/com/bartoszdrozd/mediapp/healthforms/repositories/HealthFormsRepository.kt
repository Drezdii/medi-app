package com.bartoszdrozd.mediapp.healthforms.repositories

import android.util.Log
import com.bartoszdrozd.mediapp.healthforms.dtos.AlzheimersFormDTO
import com.bartoszdrozd.mediapp.healthforms.dtos.DiabetesFormDTO
import com.bartoszdrozd.mediapp.healthforms.dtos.HeartFormDTO
import com.bartoszdrozd.mediapp.healthforms.models.FormErrorCode
import com.bartoszdrozd.mediapp.utils.Error
import com.bartoszdrozd.mediapp.utils.Result
import com.bartoszdrozd.mediapp.utils.Success
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await

class HealthFormsRepository : IHealthFormsRepository {
    override suspend fun saveDiabetes(form: DiabetesFormDTO): Result<Unit, FormErrorCode> {
        return try {
            val docId = FirebaseFirestore.getInstance().collection("diabetes").document().id
            val diabetesData = hashMapOf(
                "uid" to form.uid,
                "age" to form.age,
                "date" to form.date,
                "pregnancies" to (form.pregnancies ?: 0),
                "glucoseLevel" to form.glucoseLevel,
                "insulinLevel" to form.insulinLevel,
                "bloodPressureLevel" to form.bloodPressureLevel,
                "skinThickness" to form.skinThickness,
                "bmi" to form.bmi,
                "id" to docId
            )

            FirebaseFirestore.getInstance().collection("diabetes").document(docId).set(diabetesData).await()
            Success(Unit)
        } catch (e: FirebaseFirestoreException) {
            Error(FormErrorCode.GENERIC_ERROR)
        }
    }

    override suspend fun saveAlzheimers(form: AlzheimersFormDTO): Result<Unit, FormErrorCode> {
        return try {
            val docId = FirebaseFirestore.getInstance().collection("alzheimers").document().id
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
                "normalizeWholeBrain" to form.normalizeWholeBrain,
                "id" to docId
            )

            FirebaseFirestore.getInstance().collection("alzheimers").document(docId).set(data).await()
            Success(Unit)
        } catch (e: FirebaseFirestoreException) {
            Error(FormErrorCode.GENERIC_ERROR)
        }
    }

    override suspend fun saveHeart(form: HeartFormDTO): Result<Unit, FormErrorCode> {
        return try {
            val docId = FirebaseFirestore.getInstance().collection("heart").document().id
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
                "id" to docId
            )
            FirebaseFirestore.getInstance().collection("heart").document(docId).set(data).await()
            Success(Unit)
        } catch (e: FirebaseFirestoreException) {
            Error(FormErrorCode.GENERIC_ERROR)
        }
    }

    override suspend fun getLatestHeartForm(uid: String): Result<HeartFormDTO?, Unit> {
        return try {
            val document =
                FirebaseFirestore.getInstance().collection("heart")
                    .orderBy("date", Query.Direction.DESCENDING)
                    .limit(1)
                    .whereEqualTo("uid", uid).get()
                    .await()

            val result = if (document.isEmpty) {
                null
            } else {
                document.documents[0].toObject<HeartFormDTO>()
            }
            Success(result)
        } catch (e: FirebaseFirestoreException) {
            Log.d("TEST", e.toString())
            Error(Unit)
        }
    }

    override suspend fun getLatestAlzheimers(uid: String): Result<AlzheimersFormDTO?, Unit> {
        return try {
            val document =
                FirebaseFirestore.getInstance().collection("alzheimers")
                    .orderBy("date", Query.Direction.DESCENDING)
                    .limit(1)
                    .whereEqualTo("uid", uid).get()
                    .await()

            val result = if (document.isEmpty) {
                null
            } else {
                document.documents[0].toObject<AlzheimersFormDTO>()
            }
            Success(result)
        } catch (e: FirebaseFirestoreException) {
            Log.d("TEST", e.toString())
            Error(Unit)
        }
    }

    override suspend fun getLatestDiabetes(uid: String): Result<DiabetesFormDTO?, Unit> {
        return try {
            val document =
                FirebaseFirestore.getInstance().collection("diabetes")
                    .orderBy("date", Query.Direction.DESCENDING)
                    .limit(1)
                    .whereEqualTo("uid", uid).get()
                    .await()

            val result = if (document.isEmpty) {
                null
            } else {
                document.documents[0].toObject<DiabetesFormDTO>()
            }
            Success(result)
        } catch (e: FirebaseFirestoreException) {
            Log.d("TEST", e.toString())
            Error(Unit)
        }
    }
}