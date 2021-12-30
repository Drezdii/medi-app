package com.bartoszdrozd.mediapp.insurancepicker.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bartoszdrozd.mediapp.insurancepicker.dtos.PaymentErrorCode.*
import com.bartoszdrozd.mediapp.insurancepicker.models.InsurancePolicy
import com.bartoszdrozd.mediapp.insurancepicker.usecases.IBuyInsurancePolicyUseCase
import com.bartoszdrozd.mediapp.insurancepicker.usecases.ILoadInsurancePoliciesUseCase
import com.bartoszdrozd.mediapp.utils.Error
import com.bartoszdrozd.mediapp.utils.succeeded
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class InsurancePolicyListViewModel @Inject constructor(
    private val loadInsurancePolicies: ILoadInsurancePoliciesUseCase,
    private val buyInsurancePolicy: IBuyInsurancePolicyUseCase
) : ViewModel() {
    private val _policies: MutableLiveData<List<InsurancePolicy>> = MutableLiveData()
    private val _hasFinishedProcessing = Channel<Int>(Channel.BUFFERED)

    val policies: LiveData<List<InsurancePolicy>> = _policies

    /**
     * 1 -> Success
     *
     * 2 -> Not enough coins
     *
     * 3 -> General error
     */
    val hasFinishedProcessing = _hasFinishedProcessing.receiveAsFlow()

    init {
        viewModelScope.launch {
            loadInsurancePolicies().collect {
                _policies.value = it
            }
        }
    }

    fun buyPolicy(policy: InsurancePolicy) {
        viewModelScope.launch(Dispatchers.IO) {
            val res = buyInsurancePolicy(policy)
            if (res.succeeded) {
                _hasFinishedProcessing.send(1)
            } else {
                val processingCode = when ((res as Error).reason) {
                    BALANCE_TOO_LOW -> 2
                    GENERAL_ERROR -> 3
                    NO_WALLET -> 4
                }
                
                _hasFinishedProcessing.send(processingCode)
            }
        }
    }
}
