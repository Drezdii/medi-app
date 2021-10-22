package com.bartoszdrozd.mediapp.auth.repositories

import com.bartoszdrozd.mediapp.auth.models.User
import com.bartoszdrozd.mediapp.auth.models.AuthErrorCode
import com.bartoszdrozd.mediapp.utils.Result

interface IUsersRepository {
    suspend fun signIn(email: String, password: String): Result<User, List<AuthErrorCode>>
//    fun getCurrentUser(): Flow<User?>
}