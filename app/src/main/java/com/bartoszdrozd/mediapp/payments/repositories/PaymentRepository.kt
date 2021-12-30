package com.bartoszdrozd.mediapp.payments.repositories

import com.bartoszdrozd.mediapp.insurancepicker.dtos.PaymentErrorCode
import com.bartoszdrozd.mediapp.payments.models.CoinsBalance
import com.bartoszdrozd.mediapp.utils.Error
import com.bartoszdrozd.mediapp.utils.MediCoinContract
import com.bartoszdrozd.mediapp.utils.Result
import com.bartoszdrozd.mediapp.utils.Success
import org.web3j.crypto.Credentials
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.protocol.exceptions.TransactionException
import org.web3j.protocol.http.HttpService
import org.web3j.tx.gas.StaticGasProvider
import org.web3j.utils.Convert
import java.math.BigInteger

class PaymentRepository : IPaymentsRepository {
    private val web3 =
        Web3j.build(HttpService("https://ropsten.infura.io/v3/1010eefd8eff466d93929f8df46f4c1b"))

    private val limits =
        StaticGasProvider(
            Convert.toWei("4", Convert.Unit.GWEI).toBigInteger(),
            BigInteger.valueOf(250000L)
        )

    private val mainWalletAddress = "0xBc34Ab92d7B87fF76943268e031FE229549bD0c9"

    private val contractAddress = "0x8529f1b24Bc2fD8e5E5342cB1bf6ab700845aB49"

    //    override suspend fun testPay() {
//        val creds =
//            Credentials.create("8f1ad86dd107401352c521b8e480f245bff7588c55b3466488760d526cfc4936")
//
//        val contract =
//            MediCoinContract.load(
//                contractAddress,
//                web3,
//                creds,
//                limits
//            )
//
//        val res = contract.transfer(
//            "0x71D66c970B7E0731D071f4882CC07Aa95547f12F",
//            BigInteger.valueOf(1000000)
//        ).send()
//        Log.d("TEST", res.toString())
//
    override suspend fun getBalances(credentials: Credentials): Result<CoinsBalance, Unit> {
        val contract =
            MediCoinContract.load(
                contractAddress,
                web3,
                credentials,
                limits
            )

        return try {
            val mediCoin = contract.balanceOf(credentials.address).send()
            val ether =
                web3.ethGetBalance(credentials.address, DefaultBlockParameterName.LATEST)
                    .send().balance
            Success(
                CoinsBalance(mediCoin, ether, credentials.address)
            )
        } catch (e: Exception) {
            Error(Unit)
        }
    }

    override suspend fun pay(
        price: Float,
        credentials: Credentials
    ): Result<Unit, PaymentErrorCode> {
        return try {
            val contract =
                MediCoinContract.load(
                    contractAddress,
                    web3,
                    credentials,
                    limits
                )
            val mdcPrice = Convert.toWei(price.toString(), Convert.Unit.ETHER)
            contract.transfer(mainWalletAddress, mdcPrice.toBigInteger()).send()

            Success(Unit)
        } catch (e: TransactionException) {
            if (e.message?.contains(
                    "execution reverted: ERC20: transfer amount exceeds balance",
                    true
                ) == true
            ) {
                Error(PaymentErrorCode.BALANCE_TOO_LOW)
            } else {
                Error(PaymentErrorCode.GENERAL_ERROR)
            }
        }
    }
}