package com.bartoszdrozd.mediapp.dashboard.usecases

import com.bartoszdrozd.mediapp.auth.repositories.IUsersRepository
import com.bartoszdrozd.mediapp.insurancepicker.models.InsuranceCompany
import com.bartoszdrozd.mediapp.insurancepicker.repositories.IInsuranceCompaniesRepository
import com.bartoszdrozd.mediapp.utils.Success
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetUsersInsuranceCompanyUseCase @Inject constructor(
    private val usersRepo: IUsersRepository,
    private val insuranceRepo: IInsuranceCompaniesRepository
) : IGetUsersInsuranceCompanyUseCase {
    @ExperimentalCoroutinesApi
    override suspend fun execute(): Flow<InsuranceCompany?> {
        return usersRepo.getCurrentUserFlow().map { user ->
            val gpId = user?.details?.insuranceCompanyId ?: ""

            val res = insuranceRepo.get(gpId)
            if (res is Success) {
                res.value
            } else {
                throw Exception()
            }
        }
    }
}