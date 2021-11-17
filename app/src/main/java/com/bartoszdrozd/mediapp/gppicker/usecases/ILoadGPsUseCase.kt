package com.bartoszdrozd.mediapp.gppicker.usecases

import com.bartoszdrozd.mediapp.gppicker.models.GeneralPractitioner
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

@ExperimentalCoroutinesApi
interface ILoadGPsUseCase {
    suspend fun execute(): Flow<List<GeneralPractitioner>>
}