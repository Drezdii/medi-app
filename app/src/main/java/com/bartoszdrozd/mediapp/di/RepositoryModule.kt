package com.bartoszdrozd.mediapp.di

import com.bartoszdrozd.mediapp.auth.repositories.IUsersRepository
import com.bartoszdrozd.mediapp.auth.repositories.UsersRepository
import com.bartoszdrozd.mediapp.forms.repositories.HealthFormsRepository
import com.bartoszdrozd.mediapp.forms.repositories.IHealthFormsRepository
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
}