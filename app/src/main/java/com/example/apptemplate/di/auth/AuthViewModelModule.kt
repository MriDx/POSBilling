package com.example.apptemplate.di.auth

import com.example.apptemplate.data.remote.repository.auth.AuthRepositoryImpl
import com.example.apptemplate.domain.repository.auth.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


@Module
@InstallIn(ViewModelComponent::class)
object AuthViewModelModule {


    @Provides
    @ViewModelScoped
    fun provideAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository = authRepositoryImpl


}