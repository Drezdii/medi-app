package com.bartoszdrozd.mediapp.userprofile.usecases

import com.bartoszdrozd.mediapp.auth.repositories.IUsersRepository
import com.bartoszdrozd.mediapp.payments.repositories.IPaymentsRepository
import com.bartoszdrozd.mediapp.payments.models.CoinsBalance
import com.bartoszdrozd.mediapp.utils.Error
import com.bartoszdrozd.mediapp.utils.Result
import javax.inject.Inject

class GetCoinsBalanceUseCase @Inject constructor(
    private val usersRepo: IUsersRepository,
    private val paymentRepo: IPaymentsRepository
) : IGetCoinsBalanceUseCase {
    override suspend fun invoke(): Result<CoinsBalance, Unit> {
        val creds = usersRepo.getWalletCredentials()
        return if (creds != null) {
            paymentRepo.getBalances(creds)
        } else {
            Error(Unit)
        }
    }
}