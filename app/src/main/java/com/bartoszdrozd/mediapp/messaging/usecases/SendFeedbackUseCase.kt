package com.bartoszdrozd.mediapp.messaging.usecases

import com.bartoszdrozd.mediapp.auth.repositories.IUsersRepository
import com.bartoszdrozd.mediapp.messaging.models.FeedbackMessage
import com.bartoszdrozd.mediapp.messaging.models.Message
import com.bartoszdrozd.mediapp.messaging.repositories.IMessagesRepository
import com.bartoszdrozd.mediapp.utils.Result
import javax.inject.Inject

class SendFeedbackUseCase @Inject constructor(
    private val usersRepo: IUsersRepository,
    private val messagesRepo: IMessagesRepository
) : ISendFeedbackUseCase {
    override suspend fun invoke(rating: Int, message: String): Result<Unit, Unit> {
        val uid = usersRepo.getCurrentUser()!!.uuid
        val feedback = FeedbackMessage(rating, Message(uid, "", message))
        return messagesRepo.sendFeedback(feedback)
    }
}