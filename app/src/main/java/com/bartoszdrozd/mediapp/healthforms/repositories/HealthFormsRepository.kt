package com.bartoszdrozd.mediapp.healthforms.repositories

import android.util.Log
import com.bartoszdrozd.mediapp.healthforms.dtos.AlzheimersFormDTO
import com.bartoszdrozd.mediapp.healthforms.dtos.DiabetesFormDTO
import com.bartoszdrozd.mediapp.healthforms.dtos.HeartFormDTO
import com.bartoszdrozd.mediapp.healthforms.models.FormErrorCode
import com.bartoszdrozd.mediapp.medicalhistory.dtos.HealthFormEntryDTO
import com.bartoszdrozd.mediapp.utils.DiseaseType
import com.bartoszdrozd.mediapp.utils.Error
import com.bartoszdrozd.mediapp.utils.Result
import com.bartoszdrozd.mediapp.utils.Success
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class HealthFormsRepository : IHealthFormsRepository {
    private suspend fun saveData(uid: String, data: HashMap<String, Any?>) {
        val docId =
            FirebaseFirestore.getInstance().collection("healthforms").document(uid)
                .collection("forms")
                .document().id

        val finalData = data.plus("id" to docId)

        FirebaseFirestore.getInstance().collection("healthforms").document(uid)
            .collection("forms")
            .document(docId)
            .set(data).await()
    }

    override suspend fun saveDiabetes(form: DiabetesFormDTO): Result<Unit, FormErrorCode> {
        return try {
            val data = hashMapOf<String, Any?>(
                "uid" to form.uid,
                "age" to form.age,
                "date" to form.date,
                "pregnancies" to (form.pregnancies ?: 0),
                "glucoseLevel" to form.glucoseLevel,
                "insulinLevel" to form.insulinLevel,
                "bloodPressureLevel" to form.bloodPressureLevel,
                "skinThickness" to form.skinThickness,
                "bmi" to form.bmi,
                "diseaseType" to DiseaseType.DIABETES
            )

            saveData(form.uid, data)
            Success(Unit)
        } catch (e: FirebaseFirestoreException) {
            Error(FormErrorCode.GENERIC_ERROR)
        }
    }

    override suspend fun saveAlzheimers(form: AlzheimersFormDTO): Result<Unit, FormErrorCode> {
        return try {
            val data = hashMapOf<String, Any?>(
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
                "diseaseType" to DiseaseType.ALZHEIMERS
            )

            saveData(form.uid, data)
            Success(Unit)
        } catch (e: FirebaseFirestoreException) {
            Error(FormErrorCode.GENERIC_ERROR)
        }
    }

    override suspend fun saveHeart(form: HeartFormDTO): Result<Unit, FormErrorCode> {
        return try {
            val data = hashMapOf<String, Any?>(
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
                "diseaseType" to DiseaseType.HEART
            )

            saveData(form.uid, data)
            Success(Unit)
        } catch (e: FirebaseFirestoreException) {
            Error(FormErrorCode.GENERIC_ERROR)
        }
    }

    override suspend fun getLatestHeartForm(uid: String): Result<HeartFormDTO?, Unit> {
        return try {
            val document =
                FirebaseFirestore.getInstance().collection("healthforms")
                    .document(uid)
                    .collection("forms")
                    .orderBy("date", Query.Direction.DESCENDING)
                    .limit(1)
                    .whereEqualTo("diseaseType", DiseaseType.HEART)
                    .get()
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
                FirebaseFirestore.getInstance().collection("healthforms")
                    .document(uid)
                    .collection("forms")
                    .orderBy("date", Query.Direction.DESCENDING)
                    .limit(1)
                    .whereEqualTo("diseaseType", DiseaseType.ALZHEIMERS)
                    .get()
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
                FirebaseFirestore.getInstance().collection("healthforms")
                    .document(uid)
                    .collection("forms")
                    .orderBy("date", Query.Direction.DESCENDING)
                    .limit(1)
                    .whereEqualTo("diseaseType", DiseaseType.DIABETES)
                    .get()
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

    @ExperimentalCoroutinesApi
    override suspend fun getAllEntries(uid: String): Flow<List<HealthFormEntryDTO>> = callbackFlow {
        val collection = FirebaseFirestore.getInstance().collection("healthforms").document(uid).collection("forms")
            .orderBy("date", Query.Direction.DESCENDING)

        val listener = collection.addSnapshotListener { snapshot, ex ->
            if (ex != null) {
                throw ex
            }

            val allPredictions = mutableListOf<HealthFormEntryDTO>()

            for (document in snapshot!!.documents) {
                allPredictions.add(document.toObject<HealthFormEntryDTO>()!!)
            }

            trySend(allPredictions)
        }

        awaitClose {
            listener.remove()
        }
    }
}