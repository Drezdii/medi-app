package com.bartoszdrozd.mediapp.userprofile.usecases

import com.bartoszdrozd.mediapp.auth.repositories.IUsersRepository
import com.bartoszdrozd.mediapp.utils.Result
import javax.inject.Inject

class UnlinkGpUseCase @Inject constructor(
    private val usersRepo: IUsersRepository
) : IUnlinkGpUseCase {
    override suspend fun invoke(): Result<Unit, Unit> {
        return usersRepo.setGeneralPractitioner(null)
    }
}