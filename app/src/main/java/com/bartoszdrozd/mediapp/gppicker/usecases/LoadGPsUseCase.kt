package com.bartoszdrozd.mediapp.gppicker.usecases

import com.bartoszdrozd.mediapp.gppicker.models.GeneralPractitioner
import com.bartoszdrozd.mediapp.gppicker.repositories.IGPRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalCoroutinesApi
class LoadGPsUseCase @Inject constructor(private val gpRepository: IGPRepository) : ILoadGPsUseCase {
    override suspend fun execute(): Flow<List<GeneralPractitioner>> {
        return gpRepository.getAll()
    }
}