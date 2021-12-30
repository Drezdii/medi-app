package com.bartoszdrozd.mediapp.userprofile.usecases

import com.bartoszdrozd.mediapp.payments.models.CoinsBalance
import com.bartoszdrozd.mediapp.utils.Result
import java.math.BigInteger

interface IGetCoinsBalanceUseCase {
    suspend operator fun invoke(): Result<CoinsBalance, Unit>
}