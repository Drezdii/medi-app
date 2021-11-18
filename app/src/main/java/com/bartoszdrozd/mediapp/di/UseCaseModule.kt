package com.bartoszdrozd.mediapp.di

import com.bartoszdrozd.mediapp.auth.repositories.IUsersRepository
import com.bartoszdrozd.mediapp.auth.usecases.*
import com.bartoszdrozd.mediapp.gppicker.repositories.IGPRepository
import com.bartoszdrozd.mediapp.gppicker.usecases.ChooseGPUseCase
import com.bartoszdrozd.mediapp.gppicker.usecases.IChooseGPUseCase
import com.bartoszdrozd.mediapp.gppicker.usecases.ILoadGPsUseCase
import com.bartoszdrozd.mediapp.gppicker.usecases.LoadGPsUseCase
import com.bartoszdrozd.mediapp.healthforms.repositories.IHealthFormsRepository
import com.bartoszdrozd.mediapp.healthforms.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {
    @Provides
    @Singleton
    fun providesSignInUseCase(repo: IUsersRepository): ISignInUseCase = SignInUseCase(repo)

    @Provides
    @Singleton
    fun providesRegisterUseCase(repo: IUsersRepository): IRegisterUserUseCase =
        RegisterUserUseCase(repo)

    @Provides
    @Singleton
    fun providesResetPasswordUseCase(repo: IUsersRepository): IResetPasswordUseCase =
        ResetPasswordUseCase(repo)

    @Provides
    @Singleton
    fun providesSaveDiabetesFormUseCase(
        repo: IUsersRepository,
        formsRepo: IHealthFormsRepository
    ): ISaveDiabetesFormUseCase =
        SaveDiabetesFormUseCase(repo, formsRepo)

    @Provides
    @Singleton
    fun providesSaveAlzheimersFormUseCase(
        repo: IUsersRepository,
        formsRepo: IHealthFormsRepository
    ): ISaveAlzheimersFormUseCase = SaveAlzheimersFormUseCase(repo, formsRepo)

    @Provides
    @Singleton
    fun providesSaveHeartFormUseCase(
        repo: IUsersRepository,
        formsRepo: IHealthFormsRepository
    ): ISaveHeartFormUseCase = SaveHeartFormUseCase(repo, formsRepo)

    @Provides
    @Singleton
    @ExperimentalCoroutinesApi
    fun providesLoadGPsUseCase(repo: IGPRepository): ILoadGPsUseCase = LoadGPsUseCase(repo)

    @Provides
    @Singleton
    fun providesChooseGPUseCase(repo: IUsersRepository): IChooseGPUseCase = ChooseGPUseCase(repo)
}