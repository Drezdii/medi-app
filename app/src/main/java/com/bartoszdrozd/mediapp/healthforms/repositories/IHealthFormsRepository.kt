package com.bartoszdrozd.mediapp.healthforms.repositories

import com.bartoszdrozd.mediapp.healthforms.dtos.AlzheimersFormDTO
import com.bartoszdrozd.mediapp.healthforms.dtos.DiabetesFormDTO
import com.bartoszdrozd.mediapp.healthforms.dtos.HeartFormDTO
import com.bartoszdrozd.mediapp.healthforms.models.FormErrorCode
import com.bartoszdrozd.mediapp.medicalhistory.dtos.HealthFormEntryDTO
import com.bartoszdrozd.mediapp.utils.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

interface IHealthFormsRepository {
    suspend fun saveDiabetes(form: DiabetesFormDTO): Result<Unit, FormErrorCode>
    suspend fun saveAlzheimers(form: AlzheimersFormDTO): Result<Unit, FormErrorCode>
    suspend fun saveHeart(form: HeartFormDTO): Result<Unit, FormErrorCode>

    // Latest forms for a user with a given uid
    suspend fun getLatestHeartForm(uid: String): Result<HeartFormDTO?, Unit>
    suspend fun getLatestDiabetes(uid: String): Result<DiabetesFormDTO?, Unit>
    suspend fun getLatestAlzheimers(uid: String): Result<AlzheimersFormDTO?, Unit>

    @ExperimentalCoroutinesApi
    suspend fun getLatestEntry(uid: String): Flow<HealthFormEntryDTO?>

    @ExperimentalCoroutinesApi
    suspend fun getAllEntries(uid: String): Flow<List<HealthFormEntryDTO>>
}