package com.bartoszdrozd.mediapp.dashboard.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bartoszdrozd.mediapp.dashboard.usecases.IGetUsersGPUseCase
import com.bartoszdrozd.mediapp.dashboard.usecases.IGetUsersInsuranceCompanyUseCase
import com.bartoszdrozd.mediapp.dashboard.usecases.IGetUsersLatestFormEntryUseCase
import com.bartoszdrozd.mediapp.dashboard.usecases.IGetUsersLatestPredictionUseCase
import com.bartoszdrozd.mediapp.gppicker.models.GeneralPractitioner
import com.bartoszdrozd.mediapp.insurancepicker.models.InsuranceCompany
import com.bartoszdrozd.mediapp.medicalhistory.dtos.HealthFormEntryDTO
import com.bartoszdrozd.mediapp.predictions.dtos.PredictionDTO
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
    private val getUsersLatestPredictionUseCase: IGetUsersLatestPredictionUseCase,
    private val getUsersLatestFormEntryUseCase: IGetUsersLatestFormEntryUseCase
) : ViewModel() {
    private val _gp: MutableLiveData<GeneralPractitioner?> = MutableLiveData()
    private val _insuranceCompany: MutableLiveData<InsuranceCompany?> = MutableLiveData()
    private val _latestPrediction: MutableLiveData<PredictionDTO?> = MutableLiveData()
    private val _latestFormEntry: MutableLiveData<HealthFormEntryDTO?> = MutableLiveData()

    val gp: LiveData<GeneralPractitioner?> = _gp
    val insuranceCompany: LiveData<InsuranceCompany?> = _insuranceCompany
    val latestPrediction: LiveData<PredictionDTO?> = _latestPrediction
    val latestFormEntry: LiveData<HealthFormEntryDTO?> = _latestFormEntry

    init {
        viewModelScope.launch {
            try {
                getUsersLatestPredictionUseCase()
                    .collect {
                        _latestPrediction.value = it
                    }
            } catch (e: Exception) {
            }
        }

        viewModelScope.launch {
            try {
                getUsersLatestFormEntryUseCase().collect {
                    _latestFormEntry.value = it
                }
            } catch (e: Exception) {
            }
        }
    }
}