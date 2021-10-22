package com.bartoszdrozd.mediapp.auth.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.bartoszdrozd.mediapp.auth.models.AuthErrorCode

class RegisterViewModel : ViewModel() {
    private val _validationErrorsPageOne = MutableLiveData<List<AuthErrorCode>>(listOf())

    val validationErrorsPageOne: LiveData<List<AuthErrorCode>> = _validationErrorsPageOne
    // validation errors for page2

    // Will not move to the next page if there are validation error for the current page


    private fun addValidationError(error: AuthErrorCode) {
        _validationErrorsPageOne.value =
            _validationErrorsPageOne.value!!.plus(error)
    }

    private fun removeValidationError(error: AuthErrorCode) {
        _validationErrorsPageOne.value = _validationErrorsPageOne.value!!.filter { it != error }
    }

    fun validateEmail(email: String) {
        if (email.isBlank()) {
            addValidationError(AuthErrorCode.EMAIL_NOT_SET)
        } else {
            removeValidationError(AuthErrorCode.EMAIL_NOT_SET)
        }
    }

    fun validatePassword(password: String) {
        if (password.length < 6) {
            addValidationError(AuthErrorCode.PASSWORD_TOO_SHORT)
        } else {
            removeValidationError(AuthErrorCode.PASSWORD_TOO_SHORT)
        }
    }

    fun validateConfirmPassword(password: String, confirmPassword: String) {
        if (password != confirmPassword) {
            addValidationError(AuthErrorCode.PASSWORDS_NOT_EQUAL)
        } else {
            removeValidationError(AuthErrorCode.PASSWORDS_NOT_EQUAL)
        }
    }

    fun validateConfirmEmail(email: String, confirmEmail: String) {
        if (email != confirmEmail) {
            addValidationError(AuthErrorCode.EMAILS_NOT_EQUAL)
        } else {
            removeValidationError(AuthErrorCode.EMAILS_NOT_EQUAL)
        }
    }

    fun moveToNext() {

    }
}