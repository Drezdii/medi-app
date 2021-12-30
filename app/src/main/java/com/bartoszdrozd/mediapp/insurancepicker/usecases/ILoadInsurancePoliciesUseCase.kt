package com.bartoszdrozd.mediapp.insurancepicker.usecases

import com.bartoszdrozd.mediapp.insurancepicker.models.InsurancePolicy
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

interface ILoadInsurancePoliciesUseCase {
    @ExperimentalCoroutinesApi
    suspend operator fun invoke(): Flow<List<InsurancePolicy>>
}