package com.bartoszdrozd.mediapp.forms.repositories

import com.bartoszdrozd.mediapp.forms.dtos.DiabetesFormDTO
import com.bartoszdrozd.mediapp.forms.models.FormErrorCode
import com.bartoszdrozd.mediapp.utils.Result

interface IHealthFormsRepository {
    suspend fun saveDiabetes(form: DiabetesFormDTO): Result<Unit, FormErrorCode>
}