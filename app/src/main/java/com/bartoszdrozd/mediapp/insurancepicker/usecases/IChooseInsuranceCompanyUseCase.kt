package com.bartoszdrozd.mediapp.insurancepicker.usecases

import com.bartoszdrozd.mediapp.insurancepicker.models.InsuranceCompany
import com.bartoszdrozd.mediapp.utils.Result

interface IChooseInsuranceCompanyUseCase {
    suspend fun execute(company: InsuranceCompany): Result<Unit, Unit>
}