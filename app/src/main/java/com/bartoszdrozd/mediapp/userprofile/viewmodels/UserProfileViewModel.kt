package com.bartoszdrozd.mediapp.userprofile.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bartoszdrozd.mediapp.auth.models.UserDetails
import com.bartoszdrozd.mediapp.payments.models.CoinsBalance
import com.bartoszdrozd.mediapp.userprofile.usecases.*
import com.bartoszdrozd.mediapp.utils.Success
import com.bartoszdrozd.mediapp.utils.succeeded
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.math.BigInteger
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val getOverallHealthScore: IGetOverallHealthScoreUseCase,
    private val getUserDetails: IGetUserDetailsUseCase,
    private val unlinkGpUseCase: IUnlinkGpUseCase,
    private val unlinkInsuranceCompanyUseCase: IUnlinkInsuranceCompanyUseCase,
    private val getCoinsBalance: IGetCoinsBalanceUseCase
) :
    ViewModel() {
    private val _overallHealthScore: MutableLiveData<Float> = MutableLiveData()
    private val _userDetails: MutableLiveData<UserDetails> = MutableLiveData()
    private val _coinsBalance: MutableLiveData<CoinsBalance> = MutableLiveData()
    private val channelShowSavedToast = Channel<Int>(Channel.BUFFERED)

    val overallHealthScore: LiveData<Float> = _overallHealthScore
    val userDetails: LiveData<UserDetails> = _userDetails
    val coinsBalance: LiveData<CoinsBalance> = _coinsBalance
    val showSavedToast = channelShowSavedToast.receiveAsFlow()

    init {
        viewModelScope.launch {
            getUserDetails.execute().collect {
                _userDetails.value = it
            }
        }
        viewModelScope.launch {
            getOverallHealthScore.execute().collect {
                _overallHealthScore.value = it
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            getBalance()
        }
    }

    private suspend fun getBalance() {
        val res = getCoinsBalance()
        if (res is Success) {
            _coinsBalance.postValue(res.value)
        } else {
            _coinsBalance.postValue(
                CoinsBalance(
                    BigInteger.valueOf(0),
                    BigInteger.valueOf(0),
                    "No wallet found"
                )
            )
        }
    }

    fun unlinkGp() {
        viewModelScope.launch {
            if (unlinkGpUseCase().succeeded) {
                channelShowSavedToast.send(1)
            } else {
                channelShowSavedToast.send(0)
            }
        }
    }

    fun unlinkInsuranceCompany() {
        viewModelScope.launch {
            if (unlinkInsuranceCompanyUseCase().succeeded) {
                channelShowSavedToast.send(1)
            }
        }
    }

    fun signOut() {
        FirebaseAuth.getInstance().signOut()
    }
}