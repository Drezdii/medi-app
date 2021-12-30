package com.bartoszdrozd.mediapp.userprofile.usecases

import com.bartoszdrozd.mediapp.auth.repositories.IUsersRepository
import com.bartoszdrozd.mediapp.utils.Result
import javax.inject.Inject

class UnlinkInsuranceCompanyUseCase @Inject constructor(
    private val usersRepo: IUsersRepository
) : IUnlinkInsuranceCompanyUseCase {
    override suspend fun invoke(): Result<Unit, Unit> {
        return usersRepo.setInsuranceCompany(null)
    }
}