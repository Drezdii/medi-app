package com.bartoszdrozd.mediapp.predictions.repositories

import android.util.Log
import com.bartoszdrozd.mediapp.predictions.dtos.PredictionDTO
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

class PredictionsRepository : IPredictionsRepository {
    override suspend fun save(uid: String, prediction: PredictionDTO): Result<Unit, Unit> {
        return try {
            val docId =
                FirebaseFirestore.getInstance().collection("predictions").document(uid)
                    .collection("history")
                    .document().id

            Log.d("TEST", prediction.toString())

            val data = hashMapOf(
                "value" to prediction.value,
                "date" to prediction.date,
                "formId" to prediction.formId,
                "predictionType" to prediction.predictionType,
                "id" to docId
            )

            FirebaseFirestore.getInstance()
                .collection("predictions").document(uid)
                .collection("history").document(docId).set(data).await()
            Success(Unit)
        } catch (e: FirebaseFirestoreException) {
            Log.d("TEST", e.toString())
            Error(Unit)
        }
    }

    @ExperimentalCoroutinesApi
    override suspend fun getAllByUserId(uid: String): Flow<List<PredictionDTO>> = callbackFlow {
        val collection = FirebaseFirestore.getInstance().collection("predictions").document(uid)
            .collection("history")
            .orderBy("date", Query.Direction.DESCENDING)

        val listener = collection.addSnapshotListener { snapshot, ex ->
            if (ex != null) {
                throw ex
            }

            val allPredictions = mutableListOf<PredictionDTO>()

            for (document in snapshot!!.documents) {
                allPredictions.add(document.toObject<PredictionDTO>()!!)
            }

            trySend(allPredictions)
        }

        awaitClose {
            listener.remove()
        }
    }

    @ExperimentalCoroutinesApi
    override suspend fun getLatestPrediction(
        uid: String,
        predictionType: DiseaseType
    ): Flow<PredictionDTO?> =
        callbackFlow {
            val document = FirebaseFirestore.getInstance().collection("predictions").document(uid)
                .collection("history")
                .orderBy("date", Query.Direction.DESCENDING)
                .limit(1)
                .whereEqualTo("predictionType", predictionType)

            val listener = document.addSnapshotListener { snapshot, ex ->
                if (ex != null) {
                    throw ex
                }

                if (snapshot!!.documents.isNotEmpty()) {
                    val prediction = snapshot.documents[0].toObject<PredictionDTO>()!!
                    trySend(prediction)
                } else {
                    trySend(null)
                }
            }

            awaitClose {
                listener.remove()
            }
        }

    @ExperimentalCoroutinesApi
    override suspend fun getLatestPrediction(uid: String): Flow<PredictionDTO?> =
        callbackFlow {
            val document = FirebaseFirestore.getInstance().collection("predictions").document(uid)
                .collection("history")
                .orderBy("date", Query.Direction.DESCENDING)
                .limit(1)

            val listener = document.addSnapshotListener { snapshot, ex ->
                if (ex != null) {
                    throw ex
                }

                if (snapshot!!.documents.isNotEmpty()) {
                    val prediction = snapshot.documents[0].toObject<PredictionDTO>()
                    trySend(prediction)
                } else {
                    trySend(null)
                }
            }

            awaitClose {
                listener.remove()
            }
        }
}