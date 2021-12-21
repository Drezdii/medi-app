package com.bartoszdrozd.mediapp.messaging

import java.time.LocalDateTime
import java.time.ZoneOffset

data class Message(
    val from: String,
    val to: String,
    val message: String,
    val date: Long = LocalDateTime.now(ZoneOffset.UTC).atZone(ZoneOffset.UTC).toEpochSecond()
)
