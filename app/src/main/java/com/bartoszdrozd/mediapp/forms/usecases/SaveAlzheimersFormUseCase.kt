package com.bartoszdrozd.mediapp.forms.usecases

import com.bartoszdrozd.mediapp.auth.repositories.IUsersRepository
import com.bartoszdrozd.mediapp.forms.dtos.AlzheimersFormDTO
import com.bartoszdrozd.mediapp.forms.models.health.FormErrorCode
import com.bartoszdrozd.mediapp.forms.repositories.IHealthFormsRepository
import com.bartoszdrozd.mediapp.utils.Result
import javax.inject.Inject

class SaveAlzheimersFormUseCase @Inject constructor(
    private val userRepo: IUsersRepository,
    private val formsRepo: IHealthFormsRepository
) : ISaveAlzheimersFormUseCase {
    override suspend fun execute(form: AlzheimersFormDTO): Result<Unit, FormErrorCode> {
        val user = userRepo.getCurrentUser()

        // Add user's age to the form data
        form.age = user!!.details.age
        form.uid = user.uuid

        return formsRepo.saveAlzheimers(form)
    }
}