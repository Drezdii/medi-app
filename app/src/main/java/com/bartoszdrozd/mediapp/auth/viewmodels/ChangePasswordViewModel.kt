package com.bartoszdrozd.mediapp.auth.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bartoszdrozd.mediapp.auth.models.ChangePasswordErrorCode
import com.bartoszdrozd.mediapp.auth.usecases.IChangePasswordUseCase
import com.bartoszdrozd.mediapp.utils.Error
import com.bartoszdrozd.mediapp.utils.succeeded
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val changePasswordUseCase: IChangePasswordUseCase
) : ViewModel() {
    private val _error: MutableLiveData<ChangePasswordErrorCode> = MutableLiveData()
    private val successChannel = Channel<Int>(Channel.BUFFERED)

    val error: LiveData<ChangePasswordErrorCode> = _error
    val passwordChangedSuccess = successChannel.receiveAsFlow()

    fun changePassword(currentPassword: String, newPassword: String) {
        viewModelScope.launch {
            val res = changePasswordUseCase(currentPassword, newPassword)
            if (res.succeeded) {
                successChannel.send(1)
            } else {
                _error.value = (res as Error).reason
            }
        }
    }
}