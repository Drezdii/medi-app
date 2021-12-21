package com.bartoszdrozd.mediapp.dashboard.usecases

import com.bartoszdrozd.mediapp.auth.repositories.IUsersRepository
import com.bartoszdrozd.mediapp.healthforms.repositories.IHealthFormsRepository
import com.bartoszdrozd.mediapp.medicalhistory.dtos.HealthFormEntryDTO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUsersLatestFormEntryUseCase @Inject constructor(
    private val usersRepo: IUsersRepository,
    private val repo: IHealthFormsRepository
) :
    IGetUsersLatestFormEntryUseCase {
    @ExperimentalCoroutinesApi
    override suspend fun invoke(): Flow<HealthFormEntryDTO?> {
        val uid = usersRepo.getCurrentUser()?.uuid ?: throw Exception()

        return repo.getLatestEntry(uid)
    }
}