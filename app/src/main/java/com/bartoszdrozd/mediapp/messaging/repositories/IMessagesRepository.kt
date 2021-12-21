package com.bartoszdrozd.mediapp.messaging.repositories

import com.bartoszdrozd.mediapp.messaging.Message
import com.bartoszdrozd.mediapp.utils.Result

interface IMessagesRepository {
    suspend fun messageGp(message: Message): Result<Unit, Unit>
    suspend fun messageInsuranceCompany(message: Message): Result<Unit, Unit>
}