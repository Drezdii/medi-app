package com.bartoszdrozd.mediapp.auth.viewmodels

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bartoszdrozd.mediapp.auth.models.AuthErrorCode
import com.bartoszdrozd.mediapp.auth.usecases.IResetPasswordUseCase
import com.bartoszdrozd.mediapp.utils.Error
import com.bartoszdrozd.mediapp.utils.succeeded
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(private val resetPassword: IResetPasswordUseCase) :
    ViewModel() {
    private val _error: MutableLiveData<AuthErrorCode> = MutableLiveData()
    private val successChannel = Channel<Int>(Channel.BUFFERED)

    val resetError: LiveData<AuthErrorCode> = _error
    val resetSuccess = successChannel.receiveAsFlow()

    @SuppressLint("NullSafeMutableLiveData")
    fun resetPassword(email: String) {
        viewModelScope.launch {
            val res = resetPassword.execute(email)

            if (res.succeeded) {
                successChannel.send(1)
            } else {
                _error.value = (res as Error).reason
            }
        }
    }
}