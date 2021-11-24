package com.bartoszdrozd.mediapp.di

import com.bartoszdrozd.mediapp.auth.repositories.IUsersRepository
import com.bartoszdrozd.mediapp.auth.repositories.UsersRepository
import com.bartoszdrozd.mediapp.gppicker.repositories.GPRepository
import com.bartoszdrozd.mediapp.gppicker.repositories.IGPRepository
import com.bartoszdrozd.mediapp.healthforms.repositories.HealthFormsRepository
import com.bartoszdrozd.mediapp.healthforms.repositories.IHealthFormsRepository
import com.bartoszdrozd.mediapp.insurancepicker.repositories.IInsuranceCompaniesRepository
import com.bartoszdrozd.mediapp.insurancepicker.repositories.InsuranceCompaniesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun providesUsersRepository(): IUsersRepository = UsersRepository()

    @Provides
    @Singleton
    fun providesHealthFormsRepository(): IHealthFormsRepository = HealthFormsRepository()

    @Provides
    @Singleton
    fun providesGPRepository(): IGPRepository = GPRepository()

    @Provides
    @Singleton
    fun providesInsuranceCompaniesRepository(): IInsuranceCompaniesRepository = InsuranceCompaniesRepository()
}