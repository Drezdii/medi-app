package com.bartoszdrozd.mediapp.userprofile.usecases

import com.bartoszdrozd.mediapp.gppicker.models.GeneralPractitioner
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

interface IGetUsersGPUseCase {
    @ExperimentalCoroutinesApi
    suspend fun execute(): Flow<GeneralPractitioner?>
}