package com.bartoszdrozd.mediapp.healthforms.repositories

import com.bartoszdrozd.mediapp.healthforms.dtos.AlzheimersFormDTO
import com.bartoszdrozd.mediapp.healthforms.dtos.DiabetesFormDTO
import com.bartoszdrozd.mediapp.healthforms.dtos.HeartFormDTO
import com.bartoszdrozd.mediapp.healthforms.models.health.FormErrorCode
import com.bartoszdrozd.mediapp.utils.Result

interface IHealthFormsRepository {
    suspend fun saveDiabetes(form: DiabetesFormDTO): Result<Unit, FormErrorCode>
    suspend fun saveAlzheimers(form: AlzheimersFormDTO): Result<Unit, FormErrorCode>
    suspend fun saveHeart(form: HeartFormDTO): Result<Unit, FormErrorCode>
}