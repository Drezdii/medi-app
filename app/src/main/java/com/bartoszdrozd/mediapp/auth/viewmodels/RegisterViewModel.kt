package com.bartoszdrozd.mediapp.auth.viewmodels

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
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val registerUserUseCase: IRegisterUserUseCase) :
    ViewModel() {
    private val userDetails = RegisterUserDTO()
    private val successChannel = Channel<Int>(Channel.BUFFERED)
    val registerSuccessEvent = successChannel.receiveAsFlow()

    // Page one errors
    private val _emailError = MutableLiveData<AuthErrorCode?>()
    private val _confirmEmailError = MutableLiveData<AuthErrorCode?>()
    private val _passwordError = MutableLiveData<AuthErrorCode?>()
    private val _confirmPasswordError = MutableLiveData<AuthErrorCode?>()

    // Page two errors
    private val _firstNameError = MutableLiveData<AuthErrorCode?>()
    private val _lastNameError = MutableLiveData<AuthErrorCode?>()
    private val _sexError = MutableLiveData<AuthErrorCode?>()
    private val _dateOfBirthError = MutableLiveData<AuthErrorCode?>()
    private val _genericError = MutableLiveData<AuthErrorCode?>()

    // Page one errors public getters
    val emailError: LiveData<AuthErrorCode?> = _emailError
    val confirmEmailError: LiveData<AuthErrorCode?> = _confirmEmailError
    val passwordError: LiveData<AuthErrorCode?> = _passwordError
    val confirmPasswordError: LiveData<AuthErrorCode?> = _confirmPasswordError

    // Page two errors public getters
    val firstNameError: LiveData<AuthErrorCode?> = _firstNameError
    val lastNameError: LiveData<AuthErrorCode?> = _lastNameError
    val sexError: LiveData<AuthErrorCode?> = _sexError
    val dateOfBirthError: LiveData<AuthErrorCode?> = _dateOfBirthError
    val genericError: LiveData<AuthErrorCode?> = _genericError

    val isAccountPageValid: Boolean
        get() {
            return _emailError.value == null && _confirmEmailError.value == null && _passwordError.value == null && _confirmPasswordError.value == null
        }

    val isPersonalDetailsPageValid: Boolean
        get() {
            return _firstNameError.value == null && _lastNameError.value == null && _sexError.value == null && _dateOfBirthError.value == null
        }

    fun saveAccountDetails(userData: RegisterUserDTO) {
        userDetails.apply {
            email = userData.email
            password = userData.password
        }
    }

    fun savePersonalDetails(userData: RegisterUserDTO) {
        userDetails.apply {
            firstName = userData.firstName
            lastName = userData.lastName
            sex = userData.sex
            dateOfBirth = userData.dateOfBirth
        }
    }

    fun registerUser() {
        viewModelScope.launch {
            val res = registerUserUseCase.execute(userDetails)
            if (res is Success) {
                successChannel.send(1)
            } else if (res is Error) {
                if (res.reason == AuthErrorCode.EMAIL_IN_USE || res.reason == AuthErrorCode.INVALID_EMAIL) {
                    _emailError.value = res.reason
                } else {
                    _genericError.value = res.reason
                }
            }
        }
    }

    fun validateEmail(email: String) {
        _emailError.value = if (email.isBlank()) AuthErrorCode.REQUIRED_FIELD else null
    }

    fun validateConfirmEmail(email: String, confirmEmail: String) {
        when {
            confirmEmail.isBlank() -> _confirmEmailError.value = AuthErrorCode.REQUIRED_FIELD
            email != confirmEmail -> _confirmEmailError.value = AuthErrorCode.EMAILS_NOT_EQUAL
            else -> _confirmEmailError.value = null
        }
    }

    fun validatePassword(password: String) {
        _passwordError.value = if (password.length < 6) AuthErrorCode.PASSWORD_TOO_SHORT else null
    }

    fun validateConfirmPassword(password: String, confirmPassword: String) {
        when {
            confirmPassword.isBlank() -> _confirmPasswordError.value = AuthErrorCode.REQUIRED_FIELD
            password != confirmPassword -> _confirmPasswordError.value =
                AuthErrorCode.PASSWORDS_NOT_EQUAL
            else -> _confirmPasswordError.value = null
        }
    }

    fun validateFirstName(fName: String) {
        _firstNameError.value = if (fName.isBlank()) AuthErrorCode.REQUIRED_FIELD else null
    }

    fun validateLastName(lName: String) {
        _lastNameError.value = if (lName.isBlank()) AuthErrorCode.REQUIRED_FIELD else null
    }

    fun validateSex(selection: Int) {
        _sexError.value = if (selection == -1) AuthErrorCode.REQUIRED_FIELD else null
    }

    fun validateDateOfBirth(date: Long?) {
        _dateOfBirthError.value = if (date == null) AuthErrorCode.REQUIRED_FIELD else null
    }
}