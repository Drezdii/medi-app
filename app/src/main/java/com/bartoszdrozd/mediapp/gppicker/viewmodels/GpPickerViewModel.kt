package com.bartoszdrozd.mediapp.gppicker.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bartoszdrozd.mediapp.gppicker.models.GeneralPractitioner
import com.bartoszdrozd.mediapp.gppicker.usecases.ILoadGPsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class GpPickerViewModel @Inject constructor(private val loadGPsUseCase: ILoadGPsUseCase) : ViewModel() {
    private val _gps: MutableLiveData<List<GeneralPractitioner>> = MutableLiveData()

    val generalPractitioners: LiveData<List<GeneralPractitioner>> = _gps

    fun loadGPs() {
        viewModelScope.launch {
            loadGPsUseCase.execute().collect { allGps ->
                _gps.value = allGps
            }
        }
    }
}