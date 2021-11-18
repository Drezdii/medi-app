package com.bartoszdrozd.mediapp.gppicker.usecases

import com.bartoszdrozd.mediapp.gppicker.models.GeneralPractitioner
import com.bartoszdrozd.mediapp.utils.Result

interface IChooseGPUseCase {
    suspend fun execute(gp: GeneralPractitioner): Result<Unit, Unit>
}