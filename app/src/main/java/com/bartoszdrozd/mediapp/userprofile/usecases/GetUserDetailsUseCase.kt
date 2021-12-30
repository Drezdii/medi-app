package com.bartoszdrozd.mediapp.userprofile.usecases

import com.bartoszdrozd.mediapp.auth.models.UserDetails
import com.bartoszdrozd.mediapp.auth.repositories.IUsersRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ExperimentalCoroutinesApi
class GetUserDetailsUseCase @Inject constructor(private val repo: IUsersRepository) :
    IGetUserDetailsUseCase {

    override suspend fun execute(): Flow<UserDetails?> {
        return repo.getCurrentUserFlow().map {
            it?.details
        }
    }
}