package com.bartoszdrozd.mediapp.auth.repositories

import com.bartoszdrozd.mediapp.auth.models.User
import com.bartoszdrozd.mediapp.auth.models.AuthErrorCode
import com.bartoszdrozd.mediapp.utils.Result
import com.bartoszdrozd.mediapp.utils.Success
import com.google.firebase.auth.FirebaseAuth

class UsersRepository : IUsersRepository {
    override suspend fun signIn(
        email: String,
        password: String
    ): Result<User, List<AuthErrorCode>> {
        val res = FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
        return Success(User("fsdj23ji23ij23ji32", email))
    }
}