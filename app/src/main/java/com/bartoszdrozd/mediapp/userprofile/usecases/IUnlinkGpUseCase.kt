package com.bartoszdrozd.mediapp.userprofile.usecases

import com.bartoszdrozd.mediapp.utils.Result

interface IUnlinkGpUseCase {
    suspend operator fun invoke(): Result<Unit, Unit>
}