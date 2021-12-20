package com.bartoszdrozd.mediapp.userprofile.usecases

import com.bartoszdrozd.mediapp.auth.models.UserDetails
import com.bartoszdrozd.mediapp.auth.repositories.IUsersRepository
import javax.inject.Inject

class GetUserDetailsUseCase @Inject constructor(private val repo: IUsersRepository) : IGetUserDetailsUseCase {
    override suspend fun execute(): UserDetails? {
        return repo.getCurrentUser()?.details
    }
}