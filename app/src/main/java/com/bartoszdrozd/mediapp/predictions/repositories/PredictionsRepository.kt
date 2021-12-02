package com.bartoszdrozd.mediapp.predictions.repositories

import android.util.Log
import com.bartoszdrozd.mediapp.predictions.dtos.PredictionDTO
import com.bartoszdrozd.mediapp.utils.Error
import com.bartoszdrozd.mediapp.utils.Result
import com.bartoszdrozd.mediapp.utils.Success
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class PredictionsRepository : IPredictionsRepository {
    override suspend fun save(uuid: String, prediction: PredictionDTO): Result<Unit, Unit> {
        return try {
            Log.d("TEST", "XDD!!!! ")
            val docId =
                FirebaseFirestore.getInstance().collection("predictions").document(uuid).collection("history")
                    .document().id

            Log.d("TEST", "XDD!!!! $docId")
            val data = hashMapOf(
                "value" to prediction.value,
                "date" to prediction.date,
                "formId" to prediction.formId,
                "id" to docId
            )

            FirebaseFirestore.getInstance()
                .collection("predictions").document(uuid)
                .collection("history").document(docId).set(data).await()
            Success(Unit)
        } catch (e: FirebaseFirestoreException) {
            Log.d("TEST", e.toString())
            Error(Unit)
        }
    }

    @ExperimentalCoroutinesApi
    override suspend fun getAll(uuid: String): Flow<List<PredictionDTO>> = callbackFlow {
        val collection = FirebaseFirestore.getInstance().collection("predictions").document(uuid).collection("history")

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
}