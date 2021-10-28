package com.bartoszdrozd.mediapp.auth.usecases

import com.bartoszdrozd.mediapp.auth.dtos.RegisterUserDTO
import com.bartoszdrozd.mediapp.auth.models.AuthErrorCode
import com.bartoszdrozd.mediapp.auth.models.User
import com.bartoszdrozd.mediapp.auth.repositories.IUsersRepository
import com.bartoszdrozd.mediapp.utils.Result
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(private val repository: IUsersRepository) :
    IRegisterUserUseCase {
    override suspend fun execute(userData: RegisterUserDTO): Result<Unit, AuthErrorCode> {
        return repository.register(userData)
    }
}