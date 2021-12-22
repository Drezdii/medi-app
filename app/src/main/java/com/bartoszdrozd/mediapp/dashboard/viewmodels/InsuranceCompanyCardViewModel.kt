package com.bartoszdrozd.mediapp.dashboard.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bartoszdrozd.mediapp.dashboard.usecases.IGetUsersInsuranceCompanyUseCase
import com.bartoszdrozd.mediapp.insurancepicker.models.InsuranceCompany
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class InsuranceCompanyCardViewModel @Inject constructor(
    private val getInsuranceCompanyUseCase: IGetUsersInsuranceCompanyUseCase
) : ViewModel() {
    private val _insuranceCompany: MutableLiveData<InsuranceCompany?> = MutableLiveData()

    val insuranceCompany: LiveData<InsuranceCompany?> = _insuranceCompany

    init {
        viewModelScope.launch {
            getInsuranceCompanyUseCase.execute()
                .catch { Log.d("TEST", it.toString()) }
                .collect {
                    _insuranceCompany.value = it
                }
        }
    }
}
