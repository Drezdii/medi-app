package com.bartoszdrozd.mediapp.userprofile.usecases

import com.bartoszdrozd.mediapp.auth.models.UserDetails
import kotlinx.coroutines.flow.Flow

interface IGetUserDetailsUseCase {
    suspend fun execute(): Flow<UserDetails?>
}