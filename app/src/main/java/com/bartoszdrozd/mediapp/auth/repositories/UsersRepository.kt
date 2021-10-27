package com.bartoszdrozd.mediapp.auth.repositories

import android.util.Log
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
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class UsersRepository : IUsersRepository {
    override suspend fun signIn(
        email: String,
        password: String
    ): Result<User, List<AuthErrorCode>> {
        val res = FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
        return Success(User("fsdj23ji23ij23ji32", email))
    }

    override suspend fun register(userData: RegisterUserDTO): Result<User, AuthErrorCode> {
        return try {
            FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(userData.email, userData.password).await()
            val user = FirebaseAuth.getInstance().currentUser!!

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
                // Handle exception when saving user's personal data
            }
            Success(User(user.uid, user.email!!))
        } catch (e: FirebaseAuthException) {
            return when (e.errorCode) {
                "ERROR_EMAIL_ALREADY_IN_USE" -> Error(AuthErrorCode.EMAIL_IN_USE)
                else -> Error(AuthErrorCode.GENERIC_REGISTER_ERROR)
            }
        }
    }
}