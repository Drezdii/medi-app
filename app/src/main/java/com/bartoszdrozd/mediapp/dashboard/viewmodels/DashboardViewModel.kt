package com.bartoszdrozd.mediapp.dashboard.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bartoszdrozd.mediapp.gppicker.models.GeneralPractitioner
import com.bartoszdrozd.mediapp.insurancepicker.models.InsuranceCompany
import com.bartoszdrozd.mediapp.predictions.dtos.PredictionDTO
import com.bartoszdrozd.mediapp.userprofile.usecases.IGetUsersGPUseCase
import com.bartoszdrozd.mediapp.userprofile.usecases.IGetUsersInsuranceCompanyUseCase
import com.bartoszdrozd.mediapp.userprofile.usecases.IGetUsersLatestPredictionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getGPUseCase: IGetUsersGPUseCase,
    private val getInsuranceCompanyUseCase: IGetUsersInsuranceCompanyUseCase,
    private val getUsersLatestPredictionUseCase: IGetUsersLatestPredictionUseCase
) : ViewModel() {
    private val _gp: MutableLiveData<GeneralPractitioner?> = MutableLiveData()
    private val _insuranceCompany: MutableLiveData<InsuranceCompany?> = MutableLiveData()
    private val _latestPrediction: MutableLiveData<PredictionDTO?> = MutableLiveData()

    val gp: LiveData<GeneralPractitioner?> = _gp
    val insuranceCompany: LiveData<InsuranceCompany?> = _insuranceCompany
    val latestPrediction: LiveData<PredictionDTO?> = _latestPrediction

    init {
        viewModelScope.launch {
            getGPUseCase.execute().collect {
                _gp.value = it
            }
        }

        viewModelScope.launch {
            getInsuranceCompanyUseCase.execute().collect {
                _insuranceCompany.value = it
            }
        }

        viewModelScope.launch {
            getUsersLatestPredictionUseCase().collect {
                _latestPrediction.value = it
            }
        }
    }
}