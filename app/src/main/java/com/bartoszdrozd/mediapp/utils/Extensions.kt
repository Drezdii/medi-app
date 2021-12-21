package com.bartoszdrozd.mediapp.utils

import androidx.fragment.app.Fragment
import com.bartoszdrozd.mediapp.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

fun Long.toLocalDate(): LocalDate {
    return Instant.ofEpochSecond(this).atZone(
        ZoneId.systemDefault()
    ).toLocalDate()
}

fun Fragment.doAfterConfirmation(callback: () -> Unit) {
    MaterialAlertDialogBuilder(requireContext())
        .setTitle(resources.getString(R.string.confirm_action))
        .setMessage(resources.getString(R.string.proceed_question))
        .setNegativeButton(resources.getString(R.string.cancel)) { dialog, _ ->
            dialog.cancel()
        }
        .setPositiveButton(resources.getString(R.string.continue_text)) { _, _ ->
            callback()
        }
        .show()
}