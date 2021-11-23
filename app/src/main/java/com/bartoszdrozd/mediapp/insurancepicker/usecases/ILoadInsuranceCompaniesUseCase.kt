package com.bartoszdrozd.mediapp.insurancepicker.usecases

import com.bartoszdrozd.mediapp.insurancepicker.models.InsuranceCompany
import kotlinx.coroutines.flow.Flow

interface ILoadInsuranceCompaniesUseCase {
    suspend fun execute(): Flow<List<InsuranceCompany>>
}