package com.bartoszdrozd.mediapp.medicalhistory.usecases

import com.bartoszdrozd.mediapp.auth.repositories.IUsersRepository
import com.bartoszdrozd.mediapp.healthforms.repositories.IHealthFormsRepository
import com.bartoszdrozd.mediapp.medicalhistory.dtos.HealthFormEntryDTO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetHealthFormsHistoryUseCase @Inject constructor(
    private val repo: IHealthFormsRepository,
    private val usersRepo: IUsersRepository
) :
    IGetHealthFormsHistoryUseCase {
    @ExperimentalCoroutinesApi
    override suspend fun execute(): Flow<List<HealthFormEntryDTO>> {
        val uid = usersRepo.getCurrentUser()?.uuid ?: throw Exception()

        return repo.getAllEntries(uid)
    }
}