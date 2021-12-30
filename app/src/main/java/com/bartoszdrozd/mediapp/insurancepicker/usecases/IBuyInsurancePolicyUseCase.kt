package com.bartoszdrozd.mediapp.insurancepicker.usecases

import com.bartoszdrozd.mediapp.insurancepicker.dtos.PaymentErrorCode
import com.bartoszdrozd.mediapp.insurancepicker.models.InsurancePolicy
import com.bartoszdrozd.mediapp.utils.Result

interface IBuyInsurancePolicyUseCase {
    suspend operator fun invoke(policy: InsurancePolicy): Result<Unit, PaymentErrorCode>
}