package com.bartoszdrozd.mediapp.forms.usecases

import com.bartoszdrozd.mediapp.forms.dtos.AlzheimersFormDTO
import com.bartoszdrozd.mediapp.forms.models.health.FormErrorCode
import com.bartoszdrozd.mediapp.utils.Result

interface ISaveAlzheimersFormUseCase {
    suspend fun execute(form: AlzheimersFormDTO): Result<Unit, FormErrorCode>
}