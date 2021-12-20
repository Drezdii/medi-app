package com.bartoszdrozd.mediapp.gppicker.repositories

import com.bartoszdrozd.mediapp.gppicker.models.GeneralPractitioner
import com.bartoszdrozd.mediapp.utils.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

interface IGPRepository {
    @ExperimentalCoroutinesApi
    suspend fun getAll(): Flow<List<GeneralPractitioner>>
    suspend fun get(uid: String?): Result<GeneralPractitioner?, Unit>
}
