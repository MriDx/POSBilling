package com.example.apptemplate.di

import com.example.apptemplate.data.remote.repository.home.HomeFragmentRepositoryImpl
import com.example.apptemplate.data.remote.repository.settings.SettingsFragmentRepositoryImpl
import com.example.apptemplate.data.remote.repository.user.UserRepositoryImpl
import com.example.apptemplate.domain.repository.home.HomeFragmentRepository
import com.example.apptemplate.domain.repository.settings.SettingsFragmentRepository
import com.example.apptemplate.domain.repository.user.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {


    @Provides
    @ViewModelScoped
    fun provideHomeFragmentRepository(
        homeFragmentRepositoryImpl: HomeFragmentRepositoryImpl
    ): HomeFragmentRepository = homeFragmentRepositoryImpl


    @Provides
    @ViewModelScoped
    fun provideUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository = userRepositoryImpl


    @Provides
    @ViewModelScoped
    fun provideSettingsFragmentRepository(
        settingsFragmentRepositoryImpl: SettingsFragmentRepositoryImpl,
    ): SettingsFragmentRepository = settingsFragmentRepositoryImpl


}