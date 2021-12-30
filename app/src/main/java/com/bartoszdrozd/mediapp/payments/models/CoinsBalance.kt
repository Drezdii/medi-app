package com.bartoszdrozd.mediapp.payments.models

import java.math.BigInteger

data class CoinsBalance(val mediCoin: BigInteger, val ether: BigInteger, val wallet: String)
