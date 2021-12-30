package com.bartoszdrozd.mediapp.auth.usecases

import com.bartoszdrozd.mediapp.auth.models.ChangePasswordErrorCode
import com.bartoszdrozd.mediapp.auth.repositories.IUsersRepository
import com.bartoszdrozd.mediapp.utils.Result
import javax.inject.Inject

class ChangePasswordUseCase @Inject constructor(
    private val usersRepo: IUsersRepository
) : IChangePasswordUseCase {
    override suspend fun invoke(
        currentPassword: String,
        newPassword: String
    ): Result<Unit, ChangePasswordErrorCode> {
        return usersRepo.changePassword(currentPassword, newPassword)
    }
}