package com.bartoszdrozd.mediapp.auth.viewmodels

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bartoszdrozd.mediapp.auth.models.AuthErrorCode
import com.bartoszdrozd.mediapp.auth.usecases.ISignInUseCase
import com.bartoszdrozd.mediapp.utils.Error
import com.bartoszdrozd.mediapp.utils.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(private val signInUseCase: ISignInUseCase) : ViewModel() {
    private val _validationError = MutableLiveData<AuthErrorCode?>()
    private val successChannel = Channel<Int>(Channel.BUFFERED)

    val validationError: LiveData<AuthErrorCode?> = _validationError
    val signInSuccessEvent = successChannel.receiveAsFlow()

    private fun validateFields(email: String, password: String): Boolean {
        // Add a regex matching the one Firebase uses
        if (password.length < 6 || email.isBlank()) {
            _validationError.value = AuthErrorCode.GENERIC_SIGN_IN_ERROR
            return false
        }
        return true
    }

    fun signIn(email: String, password: String) {
        if (!validateFields(email, password)) {
            return
        }
        // Clear the previous error
        _validationError.value = null

        viewModelScope.launch {
            val res = signInUseCase.execute(email, password)
            if (res is Success) {
                successChannel.send(1)
            } else if (res is Error) {
                _validationError.value = res.reason
            }
        }
    }
}