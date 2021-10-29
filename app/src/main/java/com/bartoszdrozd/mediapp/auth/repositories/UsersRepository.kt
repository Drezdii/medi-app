package com.bartoszdrozd.mediapp.auth.repositories

import com.bartoszdrozd.mediapp.auth.dtos.RegisterUserDTO
import com.bartoszdrozd.mediapp.auth.models.User
import com.bartoszdrozd.mediapp.auth.models.AuthErrorCode
import com.bartoszdrozd.mediapp.utils.Error
import com.bartoszdrozd.mediapp.utils.Result
import com.bartoszdrozd.mediapp.utils.Success
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.tasks.await

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
                "first_name" to userData.firstName,
                "last_name" to userData.lastName,
                "gender" to userData.gender,
                "date_of_birth" to userData.dateOfBirth,
                "phone_number" to userData.phoneNumber
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
}