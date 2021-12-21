package com.bartoszdrozd.mediapp.messaging.repositories

import com.bartoszdrozd.mediapp.messaging.Message
import com.bartoszdrozd.mediapp.utils.Error
import com.bartoszdrozd.mediapp.utils.Result
import com.bartoszdrozd.mediapp.utils.Success
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.tasks.await

class MessagesRepository : IMessagesRepository {
    override suspend fun messageGp(message: Message): Result<Unit, Unit> {
        return try {
            val docId =
                FirebaseFirestore.getInstance().collection("messages").document().id

            val data = hashMapOf(
                "id" to docId,
                "from" to message.from,
                "to" to message.to,
                "message" to message.message
            )

            FirebaseFirestore.getInstance()
                .collection("messages").document(docId).set(data).await()
            Success(Unit)
        } catch (e: FirebaseFirestoreException) {
            Error(Unit)
        }
    }

    override suspend fun messageInsuranceCompany(message: Message): Result<Unit, Unit> {
        TODO("Not yet implemented")
    }
}