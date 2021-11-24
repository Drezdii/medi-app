package com.bartoszdrozd.mediapp.insurancepicker.usecases

import com.bartoszdrozd.mediapp.auth.repositories.IUsersRepository
import com.bartoszdrozd.mediapp.insurancepicker.models.InsuranceCompany
import com.bartoszdrozd.mediapp.utils.Result
import javax.inject.Inject

class ChooseInsuranceCompanyUseCase @Inject constructor(private val repo: IUsersRepository) :
    IChooseInsuranceCompanyUseCase {
    override suspend fun execute(company: InsuranceCompany): Result<Unit, Unit> {
        return repo.setInsuranceCompany(company)
    }
}