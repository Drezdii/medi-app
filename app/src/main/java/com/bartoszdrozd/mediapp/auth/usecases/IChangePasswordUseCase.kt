package com.bartoszdrozd.mediapp.auth.usecases

import com.bartoszdrozd.mediapp.auth.models.ChangePasswordErrorCode
import com.bartoszdrozd.mediapp.utils.Result

interface IChangePasswordUseCase {
    suspend operator fun invoke(
        currentPassword: String,
        newPassword: String
    ): Result<Unit, ChangePasswordErrorCode>
}