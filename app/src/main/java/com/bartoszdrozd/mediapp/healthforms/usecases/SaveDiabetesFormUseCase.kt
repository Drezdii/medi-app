package com.bartoszdrozd.mediapp.healthforms.usecases

import com.bartoszdrozd.mediapp.auth.repositories.IUsersRepository
import com.bartoszdrozd.mediapp.healthforms.dtos.DiabetesFormDTO
import com.bartoszdrozd.mediapp.healthforms.models.FormErrorCode
import com.bartoszdrozd.mediapp.healthforms.repositories.IHealthFormsRepository
import com.bartoszdrozd.mediapp.utils.Result
import javax.inject.Inject

class SaveDiabetesFormUseCase @Inject constructor(
    private val userRepo: IUsersRepository,
    private val formsRepo: IHealthFormsRepository
) :
    ISaveDiabetesFormUseCase {
    override suspend fun execute(form: DiabetesFormDTO): Result<Unit, FormErrorCode> {
        val user = userRepo.getCurrentUser()

        // Add user's age to the form data
        form.age = user!!.details.age
        form.uid = user.uuid

        return formsRepo.saveDiabetes(form)
    }
}