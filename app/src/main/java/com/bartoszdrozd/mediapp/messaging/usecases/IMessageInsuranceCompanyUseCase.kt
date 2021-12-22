package com.bartoszdrozd.mediapp.messaging.usecases

import com.bartoszdrozd.mediapp.utils.Result

interface IMessageInsuranceCompanyUseCase {
    suspend operator fun invoke(message: String): Result<Unit, Unit>
}