package com.bartoszdrozd.mediapp.gppicker.usecases

import com.bartoszdrozd.mediapp.auth.repositories.IUsersRepository
import com.bartoszdrozd.mediapp.gppicker.models.GeneralPractitioner
import com.bartoszdrozd.mediapp.utils.Result
import javax.inject.Inject

class ChooseGPUseCase @Inject constructor(private val userRepo: IUsersRepository) : IChooseGPUseCase {
    override suspend fun execute(gp: GeneralPractitioner): Result<Unit, Unit> {
        return userRepo.setGeneralPractitioner(gp)
    }
}