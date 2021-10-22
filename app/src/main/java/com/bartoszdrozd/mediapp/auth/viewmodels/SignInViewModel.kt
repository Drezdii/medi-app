package com.bartoszdrozd.mediapp.auth.viewmodels

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bartoszdrozd.mediapp.auth.models.AuthErrorCode
import com.bartoszdrozd.mediapp.auth.usecases.ISignInUseCase
import com.bartoszdrozd.mediapp.utils.Error
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(private val signInUseCase: ISignInUseCase) : ViewModel() {
    private val _validationErrors = MutableLiveData<List<AuthErrorCode>>(listOf())

    val validationErrors: LiveData<List<AuthErrorCode>> = _validationErrors

    private fun validateFields(email: String, password: String): Boolean {
        // Add a regex matching the one Firebase uses
        if (password.length < 6 || email.isBlank()) {
            _validationErrors.value =
                _validationErrors.value!!.plus(AuthErrorCode.GENERIC_SIGN_IN_ERROR)
            return false
        }
        return true
    }

    @SuppressLint("NullSafeMutableLiveData")
    fun signIn(email: String, password: String) {
        // Clear any previous errors
        _validationErrors.value = listOf()
        if (!validateFields(email, password)) {
            return
        }

        viewModelScope.launch {
            val res = signInUseCase.execute(email, password)
            if (res is Error) {
                _validationErrors.value = res.reason
            }
        }
    }
}