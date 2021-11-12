package com.bartoszdrozd.mediapp.forms.repositories

import com.bartoszdrozd.mediapp.forms.dtos.AlzheimersFormDTO
import com.bartoszdrozd.mediapp.forms.dtos.DiabetesFormDTO
import com.bartoszdrozd.mediapp.forms.models.health.FormErrorCode
import com.bartoszdrozd.mediapp.utils.Error
import com.bartoszdrozd.mediapp.utils.Result
import com.bartoszdrozd.mediapp.utils.Success
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.tasks.await

class HealthFormsRepository : IHealthFormsRepository {
    override suspend fun saveDiabetes(form: DiabetesFormDTO): Result<Unit, FormErrorCode> {
        return try {
            val diabetesData = hashMapOf(
                "uid" to form.uid,
                "age" to form.age,
                "date" to form.date,
                "pregnancies" to (form.pregnancies ?: 0),
                "glucoseLevel" to form.glucoseLevel,
                "insulinLevel" to form.insulinLevel,
                "bloodPressure" to form.bloodPressureLevel,
                "skinThickness" to form.skinThickness,
                "bmi" to form.bmi
            )

            FirebaseFirestore.getInstance().collection("diabetes").add(diabetesData).await()
            Success(Unit)
        } catch (e: FirebaseFirestoreException) {
            Error(FormErrorCode.GENERIC_ERROR)
        }
    }

    override suspend fun saveAlzheimers(form: AlzheimersFormDTO): Result<Unit, FormErrorCode> {
        return try {
            val data = hashMapOf(
                "uid" to form.uid,
                "age" to form.age,
                "date" to form.date,
                "dominantHand" to form.dominantHand,
                "miniMentalState" to form.miniMentalState,
                "clinicalDementiaRating" to form.clinicalDementiaRating,
                "socioEconomicStatus" to form.socioEconomicStatus,
                "estTotalIntracranial" to form.estTotalIntracranial,
                "normalizeWholeBrain" to form.normalizeWholeBrain
            )

            FirebaseFirestore.getInstance().collection("alzheimers").add(data).await()

            Success(Unit)
        } catch (e: FirebaseFirestoreException) {
            Error(FormErrorCode.GENERIC_ERROR)
        }
    }
}