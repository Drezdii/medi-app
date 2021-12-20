package com.bartoszdrozd.mediapp.medicalhistory.usecases

import com.bartoszdrozd.mediapp.medicalhistory.dtos.HealthFormEntryDTO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

interface IGetHealthFormsHistoryUseCase {
    @ExperimentalCoroutinesApi
    suspend fun execute(): Flow<List<HealthFormEntryDTO>>
}