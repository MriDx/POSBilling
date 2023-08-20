package dev.techmess.gym_mem.di.auth

import dev.techmess.gym_mem.data.remote.repository.auth.AuthRepositoryImpl
import dev.techmess.gym_mem.domain.repository.auth.AuthRepository
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