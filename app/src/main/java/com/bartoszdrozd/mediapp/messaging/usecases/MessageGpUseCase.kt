package com.bartoszdrozd.mediapp.messaging.usecases

import com.bartoszdrozd.mediapp.auth.repositories.IUsersRepository
import com.bartoszdrozd.mediapp.messaging.Message
import com.bartoszdrozd.mediapp.messaging.repositories.IMessagesRepository
import com.bartoszdrozd.mediapp.utils.Result
import javax.inject.Inject

class MessageGpUseCase @Inject constructor(
    private val usersRepo: IUsersRepository,
    private val messagesRepo: IMessagesRepository
) : IMessageGpUseCase {
    override suspend fun invoke(message: String): Result<Unit, Unit> {
        val uid = usersRepo.getCurrentUser()!!.uuid
        val gp = usersRepo.getCurrentUser()!!.details.gpId!!
        val msg = Message(uid, gp, message)

        return messagesRepo.messageGp(msg)
    }
}