package com.bartoszdrozd.mediapp.auth.usecases

import com.bartoszdrozd.mediapp.auth.dtos.RegisterUserDTO
import com.bartoszdrozd.mediapp.auth.models.AuthErrorCode
import com.bartoszdrozd.mediapp.auth.models.User
import com.bartoszdrozd.mediapp.utils.Result

interface IRegisterUserUseCase {
    suspend fun execute(userData: RegisterUserDTO): Result<Unit, AuthErrorCode>
}