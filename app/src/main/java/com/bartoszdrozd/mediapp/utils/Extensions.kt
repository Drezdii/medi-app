package com.bartoszdrozd.mediapp.utils

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

fun toLocalDate(date: Long): LocalDate {
    return Instant.ofEpochSecond(date).atZone(
        ZoneId.systemDefault()
    ).toLocalDate()
}