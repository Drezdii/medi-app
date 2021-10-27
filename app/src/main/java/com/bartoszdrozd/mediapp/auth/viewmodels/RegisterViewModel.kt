package com.bartoszdrozd.mediapp.auth.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bartoszdrozd.mediapp.auth.dtos.RegisterUserDTO
import com.bartoszdrozd.mediapp.auth.models.AuthErrorCode
import com.bartoszdrozd.mediapp.auth.usecases.IRegisterUserUseCase
import com.bartoszdrozd.mediapp.utils.Error
import com.bartoszdrozd.mediapp.utils.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val registerUserUseCase: IRegisterUserUseCase) :
    ViewModel() {
    private val _validationErrors = MutableLiveData<List<AuthErrorCode>>(listOf())
    private val _userData = RegisterUserDTO()

    val validationErrors: LiveData<List<AuthErrorCode>> = _validationErrors

    fun registerUser() {
        viewModelScope.launch {
            val res = registerUserUseCase.execute(_userData)
            if (res is Success) {
                Log.d("TEST", res.value.toString())
            } else {
                // Clear any client side errors and display the error returned by firebase
                _validationErrors.value = listOf((res as Error).reason)
            }
        }
    }

    fun storeAccountDetails(email: String, password: String) {
        _userData.email = email
        _userData.password = password
    }

    fun storePersonalData(
        firstName: String,
        lastName: String,
        gender: Int,
        dateOfBirth: Long?,
        phoneNumber: String?
    ) {
        _userData.firstName = firstName
        _userData.lastName = lastName
        _userData.gender = gender
        _userData.dateOfBirth = dateOfBirth
        _userData.phoneNumber = phoneNumber
    }

    fun clearServerSideErrors() {
        _validationErrors.value =
            _validationErrors.value!!.filter { it != AuthErrorCode.EMAIL_IN_USE }
    }

    private fun addValidationError(error: AuthErrorCode) {
        if (!_validationErrors.value!!.contains(error)) {
            _validationErrors.value = _validationErrors.value!!.plus(error)
        }
    }

    private fun removeValidationError(error: AuthErrorCode) {
        _validationErrors.value =
            _validationErrors.value!!.filter { it != error }
    }

    // Validate the date of birth
    fun validateDoB(selection: Long?) {
        if (selection == null) {
            addValidationError(AuthErrorCode.DOB_NOT_SET)
        } else {
            removeValidationError(AuthErrorCode.DOB_NOT_SET)
        }
    }

    fun validateGender(selection: Int) {
        if (selection == -1) {
            addValidationError(AuthErrorCode.GENDER_NOT_SET)
        } else {
            removeValidationError(AuthErrorCode.GENDER_NOT_SET)
        }
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
        if (password.isBlank() && confirmPassword.isBlank()) {
            addValidationError(AuthErrorCode.CONFIRM_PASSWORD_NOT_SET)
        } else {
            removeValidationError(AuthErrorCode.CONFIRM_PASSWORD_NOT_SET)
        }
        if (password != confirmPassword) {
            addValidationError(AuthErrorCode.PASSWORDS_NOT_EQUAL)
        } else {
            removeValidationError(AuthErrorCode.PASSWORDS_NOT_EQUAL)
        }
    }

    fun validateConfirmEmail(email: String, confirmEmail: String) {
        if (confirmEmail.isBlank()) {
            addValidationError(AuthErrorCode.CONFIRM_EMAIL_NOT_SET)
        } else {
            removeValidationError(AuthErrorCode.CONFIRM_EMAIL_NOT_SET)
        }
        if (email != confirmEmail) {
            addValidationError(AuthErrorCode.EMAILS_NOT_EQUAL)
        } else {
            removeValidationError(AuthErrorCode.EMAILS_NOT_EQUAL)
        }
    }

    fun validateFirstName(fName: String) {
        if (fName.isBlank()) {
            addValidationError(AuthErrorCode.FIRST_NAME_NOT_SET)
        } else {
            removeValidationError(AuthErrorCode.FIRST_NAME_NOT_SET)
        }
    }

    fun validateLastName(lName: String) {
        if (lName.isBlank()) {
            addValidationError(AuthErrorCode.LAST_NAME_NOT_SET)
        } else {
            removeValidationError(AuthErrorCode.LAST_NAME_NOT_SET)
        }
    }
}