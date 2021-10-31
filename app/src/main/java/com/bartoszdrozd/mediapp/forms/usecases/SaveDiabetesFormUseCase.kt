package com.bartoszdrozd.mediapp.forms.usecases

import com.bartoszdrozd.mediapp.auth.repositories.IUsersRepository
import com.bartoszdrozd.mediapp.forms.dtos.DiabetesFormDTO
import com.bartoszdrozd.mediapp.forms.models.FormErrorCode
import com.bartoszdrozd.mediapp.forms.repositories.IHealthFormsRepository
import com.bartoszdrozd.mediapp.utils.Result
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import javax.inject.Inject

class SaveDiabetesFormUseCase @Inject constructor(
    private val userRepo: IUsersRepository,
    private val formsRepo: IHealthFormsRepository
) :
    ISaveDiabetesFormUseCase {
    override suspend fun execute(form: DiabetesFormDTO): Result<Unit, FormErrorCode> {
        val details = userRepo.getCurrentUser()!!.details
        val dateOfBirth = details.dateOfBirth

        val age = ChronoUnit.YEARS.between(
            Instant.ofEpochSecond(dateOfBirth).atZone(
                ZoneId.systemDefault()
            ).toLocalDate(), LocalDate.now()
        ).toInt()

        // Add user's age to the form data
        form.age = age

        return formsRepo.saveDiabetes(form)
    }
}