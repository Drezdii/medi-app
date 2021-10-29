package com.bartoszdrozd.mediapp.auth.usecases

import com.bartoszdrozd.mediapp.auth.models.AuthErrorCode
import com.bartoszdrozd.mediapp.utils.Result

interface IResetPasswordUseCase {
    suspend fun execute(email: String): Result<Unit, AuthErrorCode>
}