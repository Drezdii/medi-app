package com.bartoszdrozd.mediapp.insurancepicker.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bartoszdrozd.mediapp.insurancepicker.models.InsuranceCompany
import com.bartoszdrozd.mediapp.insurancepicker.usecases.IChooseInsuranceCompanyUseCase
import com.bartoszdrozd.mediapp.insurancepicker.usecases.ILoadInsuranceCompaniesUseCase
import com.bartoszdrozd.mediapp.utils.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InsuranceCompanyPickerViewModel @Inject constructor(
    private val loadCompaniesUseCase: ILoadInsuranceCompaniesUseCase,
    private val saveSelectionUseCase: IChooseInsuranceCompanyUseCase
) :
    ViewModel() {
    private var _selectedCompany: MutableLiveData<InsuranceCompany> = MutableLiveData()
    private val _companies: MutableLiveData<List<InsuranceCompany>> = MutableLiveData()
    private val successChannel = Channel<Int>(Channel.BUFFERED)
    private var _isDirty: MutableLiveData<Boolean> = MutableLiveData()

    val selectedCompany: LiveData<InsuranceCompany> = _selectedCompany
    val companies: LiveData<List<InsuranceCompany>> = _companies
    val savingCompletedEvent = successChannel.receiveAsFlow()
    val isDirty: LiveData<Boolean> = _isDirty

    init {
        viewModelScope.launch {
            loadCompaniesUseCase.execute().collect {
                _companies.value = it
            }
        }
    }

    fun selectCompany(company: InsuranceCompany?) {
        // Ignore when selecting already selected company
        if (company == _selectedCompany.value) {
            return
        }
        _selectedCompany.value = company
        _isDirty.value = company != null
    }

    fun saveSelection() {
        viewModelScope.launch {
            val res = _selectedCompany.value?.let { saveSelectionUseCase.execute(it) }
            if (res is Success) {
                _isDirty.value = false
                successChannel.trySend(1)
            } else {
                successChannel.trySend(0)
            }
        }
    }
}