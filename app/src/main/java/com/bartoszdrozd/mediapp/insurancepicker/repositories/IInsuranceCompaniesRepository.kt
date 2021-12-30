package com.bartoszdrozd.mediapp.insurancepicker.repositories

import com.bartoszdrozd.mediapp.insurancepicker.models.InsuranceCompany
import com.bartoszdrozd.mediapp.insurancepicker.models.InsurancePolicy
import com.bartoszdrozd.mediapp.utils.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

interface IInsuranceCompaniesRepository {
    @ExperimentalCoroutinesApi
    suspend fun getAll(): Flow<List<InsuranceCompany>>
    suspend fun get(uid: String): Result<InsuranceCompany?, Unit>

    @ExperimentalCoroutinesApi
    suspend fun getPolicies(insuranceCompanyId: String): Flow<List<InsurancePolicy>>
}