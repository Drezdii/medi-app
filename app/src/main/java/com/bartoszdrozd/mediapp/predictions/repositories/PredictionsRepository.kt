package com.bartoszdrozd.mediapp.predictions.repositories

import android.util.Log
import com.bartoszdrozd.mediapp.predictions.dtos.PredictionDTO
import com.bartoszdrozd.mediapp.utils.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import org.web3j.crypto.Credentials
import org.web3j.protocol.Web3j
import org.web3j.protocol.http.HttpService
import org.web3j.tx.gas.StaticGasProvider
import org.web3j.utils.Convert
import java.math.BigInteger

class PredictionsRepository : IPredictionsRepository {
    override suspend fun save(uid: String, prediction: PredictionDTO): Result<Unit, Unit> {
        return try {
            val docId =
                FirebaseFirestore.getInstance().collection("predictions").document(uid)
                    .collection("history")
                    .document().id

            val data = hashMapOf(
                "value" to prediction.value,
                "date" to prediction.date,
                "formId" to prediction.formId,
                "predictionType" to prediction.predictionType,
                "id" to docId
            )

            FirebaseFirestore.getInstance()
                .collection("predictions").document(uid)
                .collection("history").document(docId).set(data).await()

            Success(Unit)
        } catch (e: FirebaseFirestoreException) {
            Log.d("TEST", e.toString())
            Error(Unit)
        }
    }

    override suspend fun saveToBlockchain(uid: String, prediction: PredictionDTO) {
        val web3 =
            Web3j.build(HttpService("https://ropsten.infura.io/v3/1010eefd8eff466d93929f8df46f4c1b"))

        val creds =
            Credentials.create("8f1ad86dd107401352c521b8e480f245bff7588c55b3466488760d526cfc4936")


        val limits =
            StaticGasProvider(
                Convert.toWei("2", Convert.Unit.GWEI).toBigInteger(),
                BigInteger.valueOf(250000L)
            )

        // 0xF463916a8ee3bA128Ef7842097f7769Cb76f4919 CURRENT CONTRACT ADDRESS

        val contract =
            PredictionContract.load(
                "0x55F756D113D67815D78e1134A31ccd167e953586",
                web3,
                creds,
                limits
            )

        contract.save(
            PredictionContract.Entry(
                uid,
                prediction.predictionType.toString(),
                prediction.value.toString()
            )
        ).send()
    }

    @ExperimentalCoroutinesApi
    override suspend fun getAllByUserId(uid: String): Flow<List<PredictionDTO>> = callbackFlow {
        val collection = FirebaseFirestore.getInstance().collection("predictions").document(uid)
            .collection("history")
            .orderBy("date", Query.Direction.DESCENDING)

        val listener = collection.addSnapshotListener { snapshot, ex ->
            if (ex != null) {
                throw ex
            }

            val allPredictions = mutableListOf<PredictionDTO>()

            for (document in snapshot!!.documents) {
                allPredictions.add(document.toObject<PredictionDTO>()!!)
            }

            trySend(allPredictions)
        }

        awaitClose {
            listener.remove()
        }
    }

    @ExperimentalCoroutinesApi
    override suspend fun getLatestPrediction(
        uid: String,
        predictionType: DiseaseType
    ): Flow<PredictionDTO?> =
        callbackFlow {
            val document = FirebaseFirestore.getInstance().collection("predictions").document(uid)
                .collection("history")
                .orderBy("date", Query.Direction.DESCENDING)
                .limit(1)
                .whereEqualTo("predictionType", predictionType)

            val listener = document.addSnapshotListener { snapshot, ex ->
                if (ex != null) {
                    throw ex
                }

                if (snapshot!!.documents.isNotEmpty()) {
                    val prediction = snapshot.documents[0].toObject<PredictionDTO>()!!
                    trySend(prediction)
                } else {
                    trySend(null)
                }
            }

            awaitClose {
                listener.remove()
            }
        }

    @ExperimentalCoroutinesApi
    override suspend fun getLatestPrediction(uid: String): Flow<PredictionDTO?> =
        callbackFlow {
            val document = FirebaseFirestore.getInstance().collection("predictions").document(uid)
                .collection("history")
                .orderBy("date", Query.Direction.DESCENDING)
                .limit(1)

            val listener = document.addSnapshotListener { snapshot, ex ->
                if (ex != null) {
                    throw ex
                }

                if (snapshot!!.documents.isNotEmpty()) {
                    val prediction = snapshot.documents[0].toObject<PredictionDTO>()
                    trySend(prediction)
                } else {
                    trySend(null)
                }
            }

            awaitClose {
                listener.remove()
            }
        }
}