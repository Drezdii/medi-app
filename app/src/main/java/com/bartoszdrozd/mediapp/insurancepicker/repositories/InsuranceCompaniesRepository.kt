package com.bartoszdrozd.mediapp.insurancepicker.repositories

import com.bartoszdrozd.mediapp.insurancepicker.models.InsuranceCompany
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

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

    override suspend fun get(uid: String): InsuranceCompany? {
        TODO("Not yet implemented")
    }
}