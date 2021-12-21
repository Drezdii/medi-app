package com.bartoszdrozd.mediapp.messaging.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bartoszdrozd.mediapp.messaging.usecases.IMessageGpUseCase
import com.bartoszdrozd.mediapp.utils.succeeded
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MessagingViewModel @Inject constructor(
    private val messageGpUseCase: IMessageGpUseCase
) : ViewModel() {
    private val successChannel = Channel<Int>(Channel.BUFFERED)
    val sendSuccess = successChannel.receiveAsFlow()

    fun sendMessageToGp(message: String) {
        viewModelScope.launch {
            val res = messageGpUseCase(message)
            if (res.succeeded) {
                successChannel.send(1)
            } else {

            }
        }
    }
}