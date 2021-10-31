package com.bartoszdrozd.mediapp.forms.usecases

import com.bartoszdrozd.mediapp.forms.dtos.DiabetesFormDTO
import com.bartoszdrozd.mediapp.forms.models.FormErrorCode
import com.bartoszdrozd.mediapp.utils.Result

interface ISaveDiabetesFormUseCase {
    suspend fun execute(form: DiabetesFormDTO): Result<Unit, FormErrorCode>
}