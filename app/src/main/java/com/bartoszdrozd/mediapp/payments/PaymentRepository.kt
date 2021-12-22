package com.bartoszdrozd.mediapp.payments

import android.util.Log
import org.web3j.protocol.Web3j
import org.web3j.protocol.http.HttpService

class PaymentRepository : IPaymentsRepository {
    override suspend fun testPay() {
        val web3 =
            Web3j.build(HttpService("https://ropsten.infura.io/v3/19c5868b083b4fbf84e374c520c32e8f"))


        val clientVersion = web3.web3ClientVersion().sendAsync().get()
        try {
            if (!clientVersion.hasError()) {
                Log.d("TEST", "CONNECTED TO ROPSTEN")
            }
        } catch (e: Exception) {

        }
    }
}