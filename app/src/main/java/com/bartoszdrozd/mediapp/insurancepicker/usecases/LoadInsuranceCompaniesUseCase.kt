package com.bartoszdrozd.mediapp.insurancepicker.usecases

import com.bartoszdrozd.mediapp.insurancepicker.models.InsuranceCompany
import com.bartoszdrozd.mediapp.insurancepicker.repositories.IInsuranceCompaniesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalCoroutinesApi
class LoadInsuranceCompaniesUseCase @Inject constructor(private val repo: IInsuranceCompaniesRepository) :
    ILoadInsuranceCompaniesUseCase {

    override suspend fun execute(): Flow<List<InsuranceCompany>> {
        return repo.getAll()
    }
}