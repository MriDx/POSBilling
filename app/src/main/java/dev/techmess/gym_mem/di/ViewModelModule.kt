package dev.techmess.gym_mem.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dev.techmess.gym_mem.data.remote.repository.home.HomeFragmentRepositoryImpl
import dev.techmess.gym_mem.data.remote.repository.member.MemberRepositoryImpl
import dev.techmess.gym_mem.data.remote.repository.settings.SettingsFragmentRepositoryImpl
import dev.techmess.gym_mem.data.remote.repository.user.UserRepositoryImpl
import dev.techmess.gym_mem.domain.repository.home.HomeFragmentRepository
import dev.techmess.gym_mem.domain.repository.member.MemberRepository
import dev.techmess.gym_mem.domain.repository.settings.SettingsFragmentRepository
import dev.techmess.gym_mem.domain.repository.user.UserRepository


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


    @Provides
    @ViewModelScoped
    fun provideMemberRepository(
        memberRepositoryImpl: MemberRepositoryImpl
    ): MemberRepository = memberRepositoryImpl


}