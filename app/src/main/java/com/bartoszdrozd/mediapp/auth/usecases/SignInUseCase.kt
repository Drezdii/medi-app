package com.bartoszdrozd.mediapp.auth.usecases

import com.bartoszdrozd.mediapp.auth.models.AuthErrorCode
import com.bartoszdrozd.mediapp.auth.repositories.IUsersRepository
import com.bartoszdrozd.mediapp.utils.Result

class SignInUseCase constructor(private val repo: IUsersRepository) : ISignInUseCase {
    override suspend fun execute(
        email: String,
        password: String
    ): Result<Unit, AuthErrorCode> {
        return repo.signIn(email, password)
    }
}