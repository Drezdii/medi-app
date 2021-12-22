package com.bartoszdrozd.mediapp.messaging.usecases

import com.bartoszdrozd.mediapp.auth.repositories.IUsersRepository
import com.bartoszdrozd.mediapp.messaging.Message
import com.bartoszdrozd.mediapp.messaging.repositories.IMessagesRepository
import com.bartoszdrozd.mediapp.utils.Result
import javax.inject.Inject

class MessageInsuranceCompanyUseCase @Inject constructor(
    private val usersRepo: IUsersRepository,
    private val messagesRepo: IMessagesRepository
) : IMessageInsuranceCompanyUseCase {
    override suspend fun invoke(message: String): Result<Unit, Unit> {
        val uid = usersRepo.getCurrentUser()!!.uuid
        val insuranceCompany = usersRepo.getCurrentUser()!!.details.insuranceCompanyId!!
        val msg = Message(uid, insuranceCompany, message)

        return messagesRepo.messageInsuranceCompany(msg)
    }
}