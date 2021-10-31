package com.bartoszdrozd.mediapp.forms.repositories

import com.bartoszdrozd.mediapp.forms.dtos.DiabetesFormDTO
import com.bartoszdrozd.mediapp.forms.models.FormErrorCode
import com.bartoszdrozd.mediapp.utils.Error
import com.bartoszdrozd.mediapp.utils.Result
import com.bartoszdrozd.mediapp.utils.Success
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.tasks.await

class HealthFormsRepository : IHealthFormsRepository {
    override suspend fun saveDiabetes(form: DiabetesFormDTO): Result<Unit, FormErrorCode> {
        return try {
            val diabetesData = hashMapOf(
                "uid" to FirebaseAuth.getInstance().currentUser!!.uid,
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
}