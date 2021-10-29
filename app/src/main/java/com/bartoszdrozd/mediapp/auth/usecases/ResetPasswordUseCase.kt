package com.bartoszdrozd.mediapp.auth.usecases

import com.bartoszdrozd.mediapp.auth.models.AuthErrorCode
import com.bartoszdrozd.mediapp.auth.repositories.IUsersRepository
import com.bartoszdrozd.mediapp.utils.Result
import javax.inject.Inject

class ResetPasswordUseCase @Inject constructor(private val repository: IUsersRepository) :
    IResetPasswordUseCase {
    override suspend fun execute(email: String): Result<Unit, AuthErrorCode> {
        return repository.resetPassword(email)
    }
}