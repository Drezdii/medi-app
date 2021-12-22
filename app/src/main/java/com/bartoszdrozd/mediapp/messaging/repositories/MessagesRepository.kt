package com.bartoszdrozd.mediapp.messaging.repositories

import com.bartoszdrozd.mediapp.messaging.models.FeedbackMessage
import com.bartoszdrozd.mediapp.messaging.models.Message
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
                FirebaseFirestore.getInstance().collection("messages_gp").document().id

            val data = hashMapOf(
                "id" to docId,
                "from" to message.from,
                "date" to message.date,
                "to" to message.to,
                "message" to message.message
            )

            FirebaseFirestore.getInstance()
                .collection("messages_gp").document(docId).set(data).await()
            Success(Unit)
        } catch (e: FirebaseFirestoreException) {
            Error(Unit)
        }
    }

    override suspend fun messageInsuranceCompany(message: Message): Result<Unit, Unit> {
        return try {
            val docId =
                FirebaseFirestore.getInstance().collection("messages_insurance").document().id

            val data = hashMapOf(
                "id" to docId,
                "from" to message.from,
                "date" to message.date,
                "message" to message.message
            )

            FirebaseFirestore.getInstance()
                .collection("messages_insurance").document(docId).set(data).await()
            Success(Unit)
        } catch (e: FirebaseFirestoreException) {
            Error(Unit)
        }
    }

    override suspend fun sendFeedback(feedback: FeedbackMessage): Result<Unit, Unit> {
        return try {
            val docId = FirebaseFirestore.getInstance().collection("feedback").document().id

            val data = hashMapOf(
                "id" to docId,
                "from" to feedback.message.from,
                "to" to feedback.message.to,
                "date" to feedback.message.date,
                "message" to feedback.message.message,
                "rating" to feedback.rating
            )

            FirebaseFirestore.getInstance().collection("feedback").document(docId).set(data).await()
            Success(Unit)
        } catch (e: FirebaseFirestoreException) {
            Error(Unit)
        }
    }
}