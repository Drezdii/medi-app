package com.bartoszdrozd.mediapp.gppicker.repositories

import android.util.Log
import com.bartoszdrozd.mediapp.gppicker.models.GeneralPractitioner
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class GPRepository : IGPRepository {
    @ExperimentalCoroutinesApi
    override suspend fun getAll(): Flow<List<GeneralPractitioner>> = callbackFlow {
        val collection = FirebaseFirestore.getInstance().collection("professionals")
        val listener = collection.addSnapshotListener { snapshot, ex ->
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

    override suspend fun get(uid: String): GeneralPractitioner? {
        TODO("Not yet implemented")
    }
}