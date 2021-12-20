package com.bartoszdrozd.mediapp.userprofile.usecases

import com.bartoszdrozd.mediapp.insurancepicker.models.InsuranceCompany
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

interface IGetUsersInsuranceCompanyUseCase {
    @ExperimentalCoroutinesApi
    suspend fun execute(): Flow<InsuranceCompany?>
}