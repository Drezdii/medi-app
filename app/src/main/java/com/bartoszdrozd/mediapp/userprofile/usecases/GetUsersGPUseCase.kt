package com.bartoszdrozd.mediapp.userprofile.usecases

import com.bartoszdrozd.mediapp.auth.repositories.IUsersRepository
import com.bartoszdrozd.mediapp.gppicker.models.GeneralPractitioner
import com.bartoszdrozd.mediapp.gppicker.repositories.IGPRepository
import com.bartoszdrozd.mediapp.utils.Success
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetUsersGPUseCase @Inject constructor(
    private val usersRepo: IUsersRepository,
    private val gpRepo: IGPRepository
) : IGetUsersGPUseCase {
    @ExperimentalCoroutinesApi
    override suspend fun execute(): Flow<GeneralPractitioner?> {
        return usersRepo.getCurrentUserFlow().map { user ->
            val gpId = user?.details?.gpId

            val res = gpRepo.get(gpId)
            if (res is Success) {
                res.value
            } else {
                throw Exception()
            }
        }
    }
}