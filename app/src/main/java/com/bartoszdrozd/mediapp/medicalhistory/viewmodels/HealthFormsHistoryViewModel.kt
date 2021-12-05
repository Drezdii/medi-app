package com.bartoszdrozd.mediapp.medicalhistory.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bartoszdrozd.mediapp.healthforms.repositories.IHealthFormsRepository
import com.bartoszdrozd.mediapp.medicalhistory.dtos.HealthFormEntryDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class HealthFormsHistoryViewModel @Inject constructor(private val repo: IHealthFormsRepository) : ViewModel() {
    private val _forms: MutableLiveData<List<HealthFormEntryDTO>> = MutableLiveData()
    val forms: LiveData<List<HealthFormEntryDTO>> get() = _forms

    init {
        viewModelScope.launch {
            repo.getAllEntries("fGOuo045iAVpfnNPSTYAF3zEHX03")
                .catch { ex -> Log.d("TEST", ex.toString()) }
                .collect {
                    _forms.value = it
                }
        }
    }
}