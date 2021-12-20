package com.bartoszdrozd.mediapp.gppicker.repositories

import com.bartoszdrozd.mediapp.gppicker.models.GeneralPractitioner
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

class GPRepository : IGPRepository {
    @ExperimentalCoroutinesApi
    override suspend fun getAll(): Flow<List<GeneralPractitioner>> = callbackFlow {
        val collection = FirebaseFirestore.getInstance().collection("professionals")
        val listener = collection.addSnapshotListener { snapshot, ex ->
            if (ex != null) {
                throw ex
            }
            val gpDocuments = snapshot!!.documents

            val allGps = mutableListOf<GeneralPractitioner>()
            for (document in gpDocuments) {
                allGps.add(document.toObject<GeneralPractitioner>()!!)
            }

            trySend(allGps)
        }

        awaitClose {
            listener.remove()
        }
    }

    override suspend fun get(uid: String?): Result<GeneralPractitioner?, Unit> {
        return try {
            if (uid == null) {
                return Success(null)
            }
            val document =
                FirebaseFirestore.getInstance().collection("professionals").document(uid).get()
                    .await()
            if (document.exists()) {
                Success(document.toObject<GeneralPractitioner>()!!)
            } else {
                Success(null)
            }
        } catch (e: FirebaseFirestoreException) {
            // Handle the exception
            Error(Unit)
        }
    }
}