package com.bartoszdrozd.mediapp.di

import com.bartoszdrozd.mediapp.auth.repositories.IUsersRepository
import com.bartoszdrozd.mediapp.auth.usecases.ISignInUseCase
import com.bartoszdrozd.mediapp.auth.usecases.SignInUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    @Singleton
    fun providesSignInUseCase(repo: IUsersRepository): ISignInUseCase = SignInUseCase(repo)
}