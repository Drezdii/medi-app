package com.bartoszdrozd.mediapp.userprofile.usecases

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

interface IGetOverallHealthScoreUseCase {
    @ExperimentalCoroutinesApi
    suspend fun execute(): Flow<Float>
}