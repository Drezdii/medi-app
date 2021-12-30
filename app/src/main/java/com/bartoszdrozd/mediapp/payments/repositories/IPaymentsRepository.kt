package com.bartoszdrozd.mediapp.payments.repositories

import com.bartoszdrozd.mediapp.insurancepicker.dtos.PaymentErrorCode
import com.bartoszdrozd.mediapp.payments.models.CoinsBalance
import com.bartoszdrozd.mediapp.utils.Result
import org.web3j.crypto.Credentials

interface IPaymentsRepository {
    suspend fun pay(price: Float, credentials: Credentials): Result<Unit, PaymentErrorCode>
    suspend fun getBalances(credentials: Credentials): Result<CoinsBalance, Unit>
}