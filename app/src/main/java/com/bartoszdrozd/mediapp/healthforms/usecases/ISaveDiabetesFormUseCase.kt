package com.bartoszdrozd.mediapp.healthforms.usecases

import com.bartoszdrozd.mediapp.healthforms.dtos.DiabetesFormDTO
import com.bartoszdrozd.mediapp.healthforms.models.FormErrorCode
import com.bartoszdrozd.mediapp.utils.Result

interface ISaveDiabetesFormUseCase {
    suspend fun execute(form: DiabetesFormDTO): Result<Unit, FormErrorCode>
}