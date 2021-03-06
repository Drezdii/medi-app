package com.bartoszdrozd.mediapp.auth.repositories

import com.bartoszdrozd.mediapp.auth.dtos.RegisterUserDTO
import com.bartoszdrozd.mediapp.auth.models.AuthErrorCode
import com.bartoszdrozd.mediapp.auth.models.ChangePasswordErrorCode
import com.bartoszdrozd.mediapp.auth.models.User
import com.bartoszdrozd.mediapp.gppicker.models.GeneralPractitioner
import com.bartoszdrozd.mediapp.insurancepicker.models.InsuranceCompany
import com.bartoszdrozd.mediapp.utils.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import org.web3j.crypto.Credentials

interface IUsersRepository {
    suspend fun signIn(email: String, password: String): Result<Unit, AuthErrorCode>
    suspend fun register(userData: RegisterUserDTO): Result<Unit, AuthErrorCode>
    suspend fun resetPassword(email: String): Result<Unit, AuthErrorCode>
    suspend fun changePassword(
        currentPassword: String,
        newPassword: String
    ): Result<Unit, ChangePasswordErrorCode>

    suspend fun getWalletCredentials(): Credentials?

    @ExperimentalCoroutinesApi
    suspend fun isLogged(): Flow<Boolean>
    suspend fun getCurrentUser(): User?

    @ExperimentalCoroutinesApi
    suspend fun getCurrentUserFlow(): Flow<User?>
    suspend fun setGeneralPractitioner(gp: GeneralPractitioner?): Result<Unit, Unit>
    suspend fun setInsuranceCompany(company: InsuranceCompany?): Result<Unit, Unit>
}