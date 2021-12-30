package com.bartoszdrozd.mediapp.insurancepicker.repositories

import com.bartoszdrozd.mediapp.insurancepicker.models.InsuranceCompany
import com.bartoszdrozd.mediapp.insurancepicker.models.InsurancePolicy
import com.bartoszdrozd.mediapp.utils.Error
import com.bartoszdrozd.mediapp.utils.Result
import com.bartoszdrozd.mediapp.utils.Success
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class InsuranceCompaniesRepository : IInsuranceCompaniesRepository {
    @ExperimentalCoroutinesApi
    override suspend fun getAll(): Flow<List<InsuranceCompany>> = callbackFlow {
        val collection = FirebaseFirestore.getInstance().collection("insurance_companies")
        val listener = collection.addSnapshotListener { snapshot, ex ->
            if (ex != null) {
                throw ex
            }

            val companiesDocs = snapshot!!.documents
            val allCompanies = mutableListOf<InsuranceCompany>()

            for (document in companiesDocs) {
                allCompanies.add(document.toObject<InsuranceCompany>()!!)
            }

            trySend(allCompanies)
        }

        awaitClose {
            listener.remove()
        }
    }

    override suspend fun get(uid: String): Result<InsuranceCompany?, Unit> {
        return try {
            val document =
                FirebaseFirestore.getInstance().collection("insurance_companies").document(uid)
                    .get()
                    .await()
            if (document.exists()) {
                Success(document.toObject<InsuranceCompany>()!!)
            } else {
                Success(null)
            }
        } catch (e: FirebaseFirestoreException) {
            // Handle the exception
            Error(Unit)
        } catch (e: IllegalArgumentException) {
            Success(null)
        }
    }

    @ExperimentalCoroutinesApi
    override suspend fun getPolicies(insuranceCompanyId: String): Flow<List<InsurancePolicy>> =
        callbackFlow {
            val collection = FirebaseFirestore.getInstance().collection("insurance_companies")
                .document(insuranceCompanyId).collection("policies")

            val listener = collection.addSnapshotListener { snapshot, ex ->
                if (ex != null) {
                    throw ex
                }

                val policiesDocs = snapshot!!.documents
                val allPolicies = mutableListOf<InsurancePolicy>()

                for (document in policiesDocs) {
                    allPolicies.add(document.toObject<InsurancePolicy>()!!)
                }

                trySend(allPolicies)
            }

            awaitClose {
                listener.remove()
            }
        }
}
