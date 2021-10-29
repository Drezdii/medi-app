package com.bartoszdrozd.mediapp.auth.models

import com.bartoszdrozd.mediapp.R

enum class AuthErrorCode(val messageResId: Int) {
    GENERIC_SIGN_IN_ERROR(R.string.incorrect_email_or_password),
    EMAIL_IN_USE(R.string.email_exists),
    PASSWORD_TOO_SHORT(R.string.password_too_short),
    EMAILS_NOT_EQUAL(R.string.emails_not_equal),
    PASSWORDS_NOT_EQUAL(R.string.passwords_not_equal),
    GENERIC_REGISTER_ERROR(R.string.generic_error),
    REQUIRED_FIELD(R.string.required_field),
    INVALID_EMAIL(R.string.invalid_email),
    GENERIC_RESET_ERROR(R.string.generic_error)
}