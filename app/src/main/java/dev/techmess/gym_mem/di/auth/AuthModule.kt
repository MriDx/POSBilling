package dev.techmess.gym_mem.di.auth

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AuthModule {


    @Provides
    @Singleton
    fun provideAuthApiService(retrofit: Retrofit): dev.techmess.gym_mem.data.remote.web_service.auth.AuthApiService {
        return retrofit.create(dev.techmess.gym_mem.data.remote.web_service.auth.AuthApiService::class.java)
    }


    @Provides
    @Singleton
    fun provideAuthApiHelper(authApiHelperImpl: dev.techmess.gym_mem.data.remote.web_service.auth.AuthApiHelperImpl): dev.techmess.gym_mem.data.remote.web_service.auth.AuthApiHelper = authApiHelperImpl


}