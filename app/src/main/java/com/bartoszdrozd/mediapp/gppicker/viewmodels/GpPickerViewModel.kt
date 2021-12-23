package com.bartoszdrozd.mediapp.gppicker.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bartoszdrozd.mediapp.gppicker.models.GeneralPractitioner
import com.bartoszdrozd.mediapp.gppicker.usecases.IChooseGPUseCase
import com.bartoszdrozd.mediapp.gppicker.usecases.ILoadGPsUseCase
import com.bartoszdrozd.mediapp.utils.succeeded
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class GpPickerViewModel @Inject constructor(
    private val loadGPsUseCase: ILoadGPsUseCase,
    private val chooseGPUseCase: IChooseGPUseCase
) : ViewModel() {
    private val successChannel = Channel<Int>(Channel.BUFFERED)
    private val _gps: MutableLiveData<List<GeneralPractitioner>> = MutableLiveData()

    // Start without a selected GP
    // If the selected gp is null that means user wants to have no GP linked to the account
    private val _selectedGP: MutableLiveData<GeneralPractitioner?> = MutableLiveData(
        GeneralPractitioner()
    )
    private var _isDirty: MutableLiveData<Boolean> = MutableLiveData()

    val generalPractitioners: LiveData<List<GeneralPractitioner>> = _gps
    val selectedGP: LiveData<GeneralPractitioner?> = _selectedGP
    val savingCompletedEvent = successChannel.receiveAsFlow()
    val isDirty: LiveData<Boolean> = _isDirty

    init {
        viewModelScope.launch {
            loadGPsUseCase.execute().collect { allGps ->
                _gps.value = allGps
            }
        }
    }

    fun selectGP(gp: GeneralPractitioner?) {
        _selectedGP.value = gp
        _isDirty.value = true
    }

    fun saveSelection() {
        viewModelScope.launch {
            val res = _selectedGP.value?.let { chooseGPUseCase.execute(it) }
            if (res?.succeeded == true) {
                successChannel.trySend(1)
            } else {
                successChannel.trySend(0)
            }
        }
    }
}