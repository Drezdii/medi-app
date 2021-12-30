package com.bartoszdrozd.mediapp.insurancepicker.usecases

import com.bartoszdrozd.mediapp.auth.repositories.IUsersRepository
import com.bartoszdrozd.mediapp.insurancepicker.models.InsurancePolicy
import com.bartoszdrozd.mediapp.insurancepicker.repositories.IInsuranceCompaniesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoadInsurancePoliciesUseCase @Inject constructor(
    private val usersRepo: IUsersRepository,
    private val insuranceRepo: IInsuranceCompaniesRepository
) :
    ILoadInsurancePoliciesUseCase {
    @ExperimentalCoroutinesApi
    override suspend fun invoke(): Flow<List<InsurancePolicy>> {
        val insuranceCompanyId = usersRepo.getCurrentUser()?.details?.insuranceCompanyId ?: ""
        return insuranceRepo.getPolicies(insuranceCompanyId)
    }
}