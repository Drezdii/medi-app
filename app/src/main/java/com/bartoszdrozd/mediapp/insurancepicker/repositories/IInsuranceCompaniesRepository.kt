package com.bartoszdrozd.mediapp.insurancepicker.repositories

import com.bartoszdrozd.mediapp.insurancepicker.models.InsuranceCompany
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

interface IInsuranceCompaniesRepository {
    @ExperimentalCoroutinesApi
    suspend fun getAll(): Flow<List<InsuranceCompany>>
    suspend fun get(uid: String): InsuranceCompany?
}