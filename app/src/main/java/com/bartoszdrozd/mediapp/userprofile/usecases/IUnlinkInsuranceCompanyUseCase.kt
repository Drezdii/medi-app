package com.bartoszdrozd.mediapp.userprofile.usecases

import com.bartoszdrozd.mediapp.utils.Result

interface IUnlinkInsuranceCompanyUseCase {
    suspend operator fun invoke(): Result<Unit, Unit>
}