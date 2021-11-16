package com.bartoszdrozd.mediapp.healthforms.usecases

import com.bartoszdrozd.mediapp.healthforms.dtos.AlzheimersFormDTO
import com.bartoszdrozd.mediapp.healthforms.models.health.FormErrorCode
import com.bartoszdrozd.mediapp.utils.Result

interface ISaveAlzheimersFormUseCase {
    suspend fun execute(form: AlzheimersFormDTO): Result<Unit, FormErrorCode>
}