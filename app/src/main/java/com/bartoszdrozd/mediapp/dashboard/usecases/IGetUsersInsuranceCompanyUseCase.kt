package com.bartoszdrozd.mediapp.dashboard.usecases

import com.bartoszdrozd.mediapp.insurancepicker.models.InsuranceCompany
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

interface IGetUsersInsuranceCompanyUseCase {
    @ExperimentalCoroutinesApi
    suspend fun execute(): Flow<InsuranceCompany?>
}