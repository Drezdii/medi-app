package com.bartoszdrozd.mediapp.healthforms.usecases

import com.bartoszdrozd.mediapp.auth.repositories.IUsersRepository
import com.bartoszdrozd.mediapp.healthforms.dtos.HeartFormDTO
import com.bartoszdrozd.mediapp.healthforms.models.FormErrorCode
import com.bartoszdrozd.mediapp.healthforms.repositories.IHealthFormsRepository
import com.bartoszdrozd.mediapp.utils.Result
import javax.inject.Inject

class SaveHeartFormUseCase @Inject constructor(
    private val userRepo: IUsersRepository,
    private val repo: IHealthFormsRepository
) : ISaveHeartFormUseCase {
    override suspend fun execute(form: HeartFormDTO): Result<Unit, FormErrorCode> {
        val user = userRepo.getCurrentUser()

        // Add user's age to the form data
        form.age = user!!.details.age
        form.uid = user.uuid
        form.gender = user.details.sex

        return repo.saveHeart(form)
    }
}