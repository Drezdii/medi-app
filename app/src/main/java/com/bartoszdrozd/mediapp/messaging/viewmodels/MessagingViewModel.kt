package com.bartoszdrozd.mediapp.messaging.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bartoszdrozd.mediapp.messaging.usecases.IMessageGpUseCase
import com.bartoszdrozd.mediapp.messaging.usecases.IMessageInsuranceCompanyUseCase
import com.bartoszdrozd.mediapp.utils.succeeded
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MessagingViewModel @Inject constructor(
    private val messageGpUseCase: IMessageGpUseCase,
    private val messageInsuranceCompanyUseCase: IMessageInsuranceCompanyUseCase
) : ViewModel() {
    private val successChannelGp = Channel<Int>(Channel.BUFFERED)
    private val successChannelInsurance = Channel<Int>(Channel.BUFFERED)

    val sendSuccessGp = successChannelGp.receiveAsFlow()
    val sendSuccessInsurance = successChannelInsurance.receiveAsFlow()

    fun sendMessageToGp(message: String) {
        viewModelScope.launch {
            val res = messageGpUseCase(message)
            if (res.succeeded) {
                successChannelGp.send(1)
            } else {

            }
        }
    }

    fun sendMessageToInsuranceCompany(message: String) {
        viewModelScope.launch {
            val res = messageInsuranceCompanyUseCase(message)
            if (res.succeeded) {
                successChannelInsurance.send(1)
            } else {

            }
        }
    }
}