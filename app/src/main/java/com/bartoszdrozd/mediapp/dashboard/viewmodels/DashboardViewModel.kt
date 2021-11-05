package com.bartoszdrozd.mediapp.dashboard.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bartoszdrozd.mediapp.auth.repositories.IUsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
// Inject use case instead
class DashboardViewModel @Inject constructor(private val repo: IUsersRepository) : ViewModel() {
    private val _name = MutableLiveData<String?>()

    val name: LiveData<String?> = _name

    init {
        viewModelScope.launch {
            val user = repo.getCurrentUser()
            _name.value = user.details.firstName + " " + user.details.lastName
        }
    }
}