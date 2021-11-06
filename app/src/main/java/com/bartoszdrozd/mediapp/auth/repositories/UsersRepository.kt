package com.bartoszdrozd.mediapp.auth.repositories

import android.util.Log
import com.bartoszdrozd.mediapp.auth.dtos.RegisterUserDTO
import com.bartoszdrozd.mediapp.auth.models.User
import com.bartoszdrozd.mediapp.auth.models.AuthErrorCode
import com.bartoszdrozd.mediapp.auth.models.UserDetails
import com.bartoszdrozd.mediapp.utils.Error
import com.bartoszdrozd.mediapp.utils.Result
import com.bartoszdrozd.mediapp.utils.Success
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.ChronoUnit

class UsersRepository : IUsersRepository {
    override suspend fun signIn(
        email: String,
        password: String
    ): Result<Unit, AuthErrorCode> {
        return try {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).await()
            Success(Unit)
        } catch (e: Exception) {
            Error(AuthErrorCode.GENERIC_SIGN_IN_ERROR)
        }
    }

    override suspend fun register(userData: RegisterUserDTO): Result<Unit, AuthErrorCode> {
        return try {
            FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(userData.email, userData.password).await()

            val userProfile = hashMapOf(
                "firstName" to userData.firstName,
                "lastName" to userData.lastName,
                "sex" to userData.sex,
                "dateOfBirth" to userData.dateOfBirth,
                "phoneNumber" to userData.phoneNumber
            )

            try {
                val userId = FirebaseAuth.getInstance().currentUser!!.uid
                FirebaseFirestore.getInstance().collection("users").document(userId)
                    .set(userProfile).await()
            } catch (e: FirebaseFirestoreException) {
                // Handle exceptions when saving user's personal data
            }

            Success(Unit)
        } catch (e: FirebaseAuthException) {
            return when (e.errorCode) {
                "ERROR_EMAIL_ALREADY_IN_USE" -> Error(AuthErrorCode.EMAIL_IN_USE)
                "ERROR_INVALID_EMAIL" -> Error(AuthErrorCode.INVALID_EMAIL)
                else -> Error(AuthErrorCode.GENERIC_REGISTER_ERROR)
            }
        } catch (e: Exception) {
            return Error(AuthErrorCode.GENERIC_REGISTER_ERROR)
        }
    }

    override suspend fun resetPassword(email: String): Result<Unit, AuthErrorCode> {
        return try {
            FirebaseAuth.getInstance().sendPasswordResetEmail(email).await()
            Success(Unit)
        } catch (e: FirebaseAuthException) {
            val error = when (e.errorCode) {
                "ERROR_INVALID_EMAIL" -> AuthErrorCode.INVALID_EMAIL
                else -> AuthErrorCode.GENERIC_RESET_ERROR
            }
            Error(error)
        }
    }

    private suspend fun getUserDetails(uid: String): UserDetails? {
        try {
            val document =
                FirebaseFirestore.getInstance().collection("users").document(uid).get().await()
            if (document.exists()) {
                val details = document.toObject<UserDetails>()!!

                details.age = ChronoUnit.YEARS.between(
                    Instant.ofEpochSecond(details.dateOfBirth).atZone(
                        ZoneId.systemDefault()
                    ).toLocalDate(),
                    LocalDate.now()
                ).toInt()

                return details
            }
        } catch (e: FirebaseFirestoreException) {
            // Handle the exception
        }
        return null
    }

    @ExperimentalCoroutinesApi
    override suspend fun isLogged(): Flow<Boolean> {
        return callbackFlow {
            val callback = FirebaseAuth.AuthStateListener {
                if (it.currentUser == null) {
                    trySend(false)
                } else {
                    trySendBlocking(true)
                }
            }
            FirebaseAuth.getInstance().addAuthStateListener(callback)
            awaitClose { FirebaseAuth.getInstance().removeAuthStateListener(callback) }
        }
    }

    override suspend fun getCurrentUser(): User? {
        return coroutineScope {
            val user = FirebaseAuth.getInstance().currentUser
            user?.let {
                val userDetails = getUserDetails(it.uid)
                User(it.uid, it.email!!, userDetails!!)
            }
        }
    }
}