package com.bartoszdrozd.mediapp.healthforms.usecases

import com.bartoszdrozd.mediapp.healthforms.dtos.HeartFormDTO
import com.bartoszdrozd.mediapp.healthforms.models.FormErrorCode
import com.bartoszdrozd.mediapp.utils.Result

interface ISaveHeartFormUseCase {
    suspend fun execute(form: HeartFormDTO): Result<Unit, FormErrorCode>
}