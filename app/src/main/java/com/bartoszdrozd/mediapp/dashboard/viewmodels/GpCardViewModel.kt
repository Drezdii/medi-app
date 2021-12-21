package com.bartoszdrozd.mediapp.dashboard.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bartoszdrozd.mediapp.dashboard.usecases.IGetUsersGPUseCase
import com.bartoszdrozd.mediapp.gppicker.models.GeneralPractitioner
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class GpCardViewModel @Inject constructor(
    private val getGPUseCase: IGetUsersGPUseCase,
) : ViewModel() {
    private val _gp: MutableLiveData<GeneralPractitioner?> = MutableLiveData()

    val gp: LiveData<GeneralPractitioner?> = _gp

    init {
        viewModelScope.launch {
            getGPUseCase.execute()
                .catch {
                    Log.d("TEST", "Exception")
                }
                .collect {
                    _gp.value = it
                }
        }
    }
}