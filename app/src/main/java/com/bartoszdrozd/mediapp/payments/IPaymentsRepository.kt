package com.bartoszdrozd.mediapp.payments

interface IPaymentsRepository {
    suspend fun testPay()
}