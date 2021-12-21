package com.bartoszdrozd.mediapp.di

import com.bartoszdrozd.mediapp.auth.repositories.IUsersRepository
import com.bartoszdrozd.mediapp.auth.usecases.*
import com.bartoszdrozd.mediapp.dashboard.usecases.*
import com.bartoszdrozd.mediapp.gppicker.repositories.IGPRepository
import com.bartoszdrozd.mediapp.gppicker.usecases.ChooseGPUseCase
import com.bartoszdrozd.mediapp.gppicker.usecases.IChooseGPUseCase
import com.bartoszdrozd.mediapp.gppicker.usecases.ILoadGPsUseCase
import com.bartoszdrozd.mediapp.gppicker.usecases.LoadGPsUseCase
import com.bartoszdrozd.mediapp.healthforms.repositories.IHealthFormsRepository
import com.bartoszdrozd.mediapp.healthforms.usecases.*
import com.bartoszdrozd.mediapp.insurancepicker.repositories.IInsuranceCompaniesRepository
import com.bartoszdrozd.mediapp.insurancepicker.usecases.ChooseInsuranceCompanyUseCase
import com.bartoszdrozd.mediapp.insurancepicker.usecases.IChooseInsuranceCompanyUseCase
import com.bartoszdrozd.mediapp.insurancepicker.usecases.ILoadInsuranceCompaniesUseCase
import com.bartoszdrozd.mediapp.insurancepicker.usecases.LoadInsuranceCompaniesUseCase
import com.bartoszdrozd.mediapp.medicalhistory.usecases.GetHealthFormsHistoryUseCase
import com.bartoszdrozd.mediapp.medicalhistory.usecases.GetPredictionsHistoryUseCase
import com.bartoszdrozd.mediapp.medicalhistory.usecases.IGetHealthFormsHistoryUseCase
import com.bartoszdrozd.mediapp.medicalhistory.usecases.IGetPredictionsHistoryUseCase
import com.bartoszdrozd.mediapp.predictions.repositories.IPredictionModelsRepository
import com.bartoszdrozd.mediapp.predictions.repositories.IPredictionsRepository
import com.bartoszdrozd.mediapp.predictions.usecases.*
import com.bartoszdrozd.mediapp.userprofile.usecases.GetOverallHealthScoreUseCase
import com.bartoszdrozd.mediapp.userprofile.usecases.GetUserDetailsUseCase
import com.bartoszdrozd.mediapp.userprofile.usecases.IGetOverallHealthScoreUseCase
import com.bartoszdrozd.mediapp.userprofile.usecases.IGetUserDetailsUseCase
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

    @ExperimentalCoroutinesApi
    @Provides
    @Singleton
    fun providesLoadInsuranceCompaniesUseCase(repo: IInsuranceCompaniesRepository): ILoadInsuranceCompaniesUseCase =
        LoadInsuranceCompaniesUseCase(repo)

    @Provides
    @Singleton
    fun providesChooseInsuranceCompanyUseCase(repo: IUsersRepository): IChooseInsuranceCompanyUseCase =
        ChooseInsuranceCompanyUseCase(repo)

    @Provides
    @Singleton
    fun providesGetHeartDiseasePredictionUseCase(
        modelRepo: IPredictionModelsRepository
    ): IGetHeartDiseasePredictionUseCase =
        GetHeartDiseasePredictionUseCase(modelRepo)

    @Provides
    @Singleton
    fun providesDiabetesPredictionUseCase(modelRepo: IPredictionModelsRepository): IGetDiabetesPredictionUseCase =
        GetDiabetesPredictionUseCase(modelRepo)

    @Provides
    @Singleton
    fun providesAlzheimersPredictionUseCase(modelRepo: IPredictionModelsRepository): IGetAlzheimersPredictionUseCase =
        GetAlzheimersPredictionUseCase(modelRepo)

    @Provides
    @Singleton
    fun providesSavePredictionUseCase(predRepo: IPredictionsRepository): ISavePredictionUseCase =
        SavePredictionUseCase(predRepo)

    @Provides
    @Singleton
    fun providesGetPredictionUseCase(
        userRepo: IUsersRepository,
        healthFormsRepo: IHealthFormsRepository,
        heartPredictionUseCase: IGetHeartDiseasePredictionUseCase,
        diabetesPredictionUseCase: IGetDiabetesPredictionUseCase,
        alzheimersPredictionUseCase: IGetAlzheimersPredictionUseCase,
        savePredictionUseCase: ISavePredictionUseCase
    ): IGetPredictionUseCase =
        GetPredictionUseCase(
            userRepo,
            healthFormsRepo,
            heartPredictionUseCase,
            diabetesPredictionUseCase,
            alzheimersPredictionUseCase,
            savePredictionUseCase
        )

    @Provides
    @Singleton
    fun providesGetHealthFormsHistoryUseCase(
        repo: IHealthFormsRepository,
        usersRepo: IUsersRepository
    ): IGetHealthFormsHistoryUseCase = GetHealthFormsHistoryUseCase(repo, usersRepo)

    @Provides
    @Singleton
    fun providesGetPredictionsHistoryUseCase(
        usersRepo: IUsersRepository,
        predictionsRepo: IPredictionsRepository
    ): IGetPredictionsHistoryUseCase = GetPredictionsHistoryUseCase(usersRepo, predictionsRepo)

    @Provides
    @Singleton
    fun providesGetOverallHealthScoreUseCase(
        repo: IPredictionsRepository,
        usersRepo: IUsersRepository
    ): IGetOverallHealthScoreUseCase = GetOverallHealthScoreUseCase(repo, usersRepo)

    @Provides
    @Singleton
    fun providesGetUserDetailsUseCase(
        repo: IUsersRepository
    ): IGetUserDetailsUseCase = GetUserDetailsUseCase(repo)

    @Provides
    @Singleton
    fun providesGetUsersGPUseCase(
        usersRepo: IUsersRepository,
        gpRepo: IGPRepository
    ): IGetUsersGPUseCase = GetUsersGPUseCase(usersRepo, gpRepo)

    @Provides
    @Singleton
    fun providesGetUsersInsuranceCompanyUseCase(
        usersRepo: IUsersRepository,
        insuranceCompaniesRepo: IInsuranceCompaniesRepository
    ): IGetUsersInsuranceCompanyUseCase =
        GetUsersInsuranceCompanyUseCase(usersRepo, insuranceCompaniesRepo)

    @Provides
    @Singleton
    fun providesGetUsersLatestPredictionUseCase(
        usersRepo: IUsersRepository,
        predictionsRepo: IPredictionsRepository
    ): IGetUsersLatestPredictionUseCase =
        GetUsersLatestPredictionUseCase(usersRepo, predictionsRepo)

    @Provides
    @Singleton
    fun providesGetUsersLatestFormEntryUseCase(
        usersRepo: IUsersRepository,
        healthFormsRepo: IHealthFormsRepository
    ): IGetUsersLatestFormEntryUseCase = GetUsersLatestFormEntryUseCase(usersRepo, healthFormsRepo)
}