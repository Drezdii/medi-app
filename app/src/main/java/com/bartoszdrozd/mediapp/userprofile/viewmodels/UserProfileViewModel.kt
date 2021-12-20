package com.bartoszdrozd.mediapp.userprofile.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bartoszdrozd.mediapp.auth.models.UserDetails
import com.bartoszdrozd.mediapp.userprofile.usecases.IGetOverallHealthScoreUseCase
import com.bartoszdrozd.mediapp.userprofile.usecases.IGetUserDetailsUseCase
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val getOverallHealthScoreUseCase: IGetOverallHealthScoreUseCase,
    private val getUserDetailsUseCase: IGetUserDetailsUseCase
) :
    ViewModel() {
    private val _overallHealthScore: MutableLiveData<Float> = MutableLiveData()
    private val _userDetails: MutableLiveData<UserDetails> = MutableLiveData()

    val overallHealthScore: LiveData<Float> = _overallHealthScore
    val userDetails: LiveData<UserDetails> = _userDetails

    init {
        viewModelScope.launch {
            _userDetails.value = getUserDetailsUseCase.execute()
            getOverallHealthScoreUseCase.execute().collect {
                _overallHealthScore.value = it
            }
        }
    }

    fun signOut() {
        FirebaseAuth.getInstance().signOut()
    }
}