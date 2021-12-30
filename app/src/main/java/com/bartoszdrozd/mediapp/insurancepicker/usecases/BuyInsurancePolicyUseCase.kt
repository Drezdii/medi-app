package com.bartoszdrozd.mediapp.insurancepicker.usecases

import com.bartoszdrozd.mediapp.auth.repositories.IUsersRepository
import com.bartoszdrozd.mediapp.insurancepicker.dtos.PaymentErrorCode
import com.bartoszdrozd.mediapp.insurancepicker.models.InsurancePolicy
import com.bartoszdrozd.mediapp.payments.repositories.IPaymentsRepository
import com.bartoszdrozd.mediapp.utils.Error
import com.bartoszdrozd.mediapp.utils.Result
import javax.inject.Inject

class BuyInsurancePolicyUseCase @Inject constructor(
    private val usersRepo: IUsersRepository,
    private val paymentRepo: IPaymentsRepository
) :
    IBuyInsurancePolicyUseCase {
    override suspend fun invoke(policy: InsurancePolicy): Result<Unit, PaymentErrorCode> {
        val credentials =
            usersRepo.getWalletCredentials() ?: return Error(PaymentErrorCode.NO_WALLET)

        return paymentRepo.pay(policy.price, credentials)
    }
}