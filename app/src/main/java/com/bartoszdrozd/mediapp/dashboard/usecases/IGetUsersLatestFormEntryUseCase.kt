package com.bartoszdrozd.mediapp.dashboard.usecases

import com.bartoszdrozd.mediapp.medicalhistory.dtos.HealthFormEntryDTO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

interface IGetUsersLatestFormEntryUseCase {
    @ExperimentalCoroutinesApi
    suspend operator fun invoke(): Flow<HealthFormEntryDTO?>
}