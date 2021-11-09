package com.bartoszdrozd.mediapp.forms.usecases

import com.bartoszdrozd.mediapp.auth.repositories.IUsersRepository
import com.bartoszdrozd.mediapp.forms.dtos.DiabetesFormDTO
import com.bartoszdrozd.mediapp.forms.models.health.FormErrorCode
import com.bartoszdrozd.mediapp.forms.repositories.IHealthFormsRepository
import com.bartoszdrozd.mediapp.utils.Result
import java.time.LocalDateTime
import java.time.ZoneOffset
import javax.inject.Inject

class SaveDiabetesFormUseCase @Inject constructor(
    private val userRepo: IUsersRepository,
    private val formsRepo: IHealthFormsRepository
) :
    ISaveDiabetesFormUseCase {
    override suspend fun execute(form: DiabetesFormDTO): Result<Unit, FormErrorCode> {
        val user = userRepo.getCurrentUser()
        val now = LocalDateTime.now(ZoneOffset.UTC)

        // Add user's age to the form data
        form.age = user!!.details.age
        form.uid = user.uuid
        form.date = now.atZone(ZoneOffset.UTC).toEpochSecond()

        return formsRepo.saveDiabetes(form)
    }
}