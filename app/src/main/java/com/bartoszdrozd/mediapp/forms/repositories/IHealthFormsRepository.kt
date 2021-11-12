package com.bartoszdrozd.mediapp.forms.repositories

import com.bartoszdrozd.mediapp.forms.dtos.AlzheimersFormDTO
import com.bartoszdrozd.mediapp.forms.dtos.DiabetesFormDTO
import com.bartoszdrozd.mediapp.forms.models.health.FormErrorCode
import com.bartoszdrozd.mediapp.utils.Result

interface IHealthFormsRepository {
    suspend fun saveDiabetes(form: DiabetesFormDTO): Result<Unit, FormErrorCode>
    suspend fun saveAlzheimers(form: AlzheimersFormDTO): Result<Unit, FormErrorCode>
}