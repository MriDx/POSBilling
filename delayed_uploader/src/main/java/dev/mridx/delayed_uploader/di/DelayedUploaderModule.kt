package dev.mridx.delayed_uploader.di

import android.content.Context
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.mridx.delayed_uploader.data.remote.repository.DURepositoryImpl
import dev.mridx.delayed_uploader.data.remote.web_service.DUApiHelper
import dev.mridx.delayed_uploader.data.remote.web_service.DUApiHelperImpl
import dev.mridx.delayed_uploader.data.remote.web_service.DUApiService
import dev.mridx.delayed_uploader.db.dao.DelayedUploaderDao
import dev.mridx.delayed_uploader.db.database.DelayedUploaderDatabase
import dev.mridx.delayed_uploader.domain.repository.DURepository
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DelayedUploaderModule {


    @Provides
    @Singleton
    @DuRetrofit
    fun provideRetrofit(
        gson: Gson,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(OkHttpClient().newBuilder().build())
            .build()
    }


    @Provides
    @Singleton
    fun provideDuApiService(retrofit: Retrofit): DUApiService =
        retrofit.create(DUApiService::class.java)


    @Provides
    @Singleton
    fun provideDuApiHelper(duApiHelperImpl: DUApiHelperImpl): DUApiHelper = duApiHelperImpl


    @Provides
    @Singleton
    fun provideDelayedUploaderDatabase(@ApplicationContext context: Context): DelayedUploaderDatabase {
        return DelayedUploaderDatabase.initialize(context)
    }

    @Provides
    @Singleton
    fun provideDelayedUploaderDao(delayedUploaderDatabase: DelayedUploaderDatabase): DelayedUploaderDao =
        delayedUploaderDatabase.delayedUploaderDao()


    @Provides
    @Singleton
    fun provideDuRepository(duRepositoryImpl: DURepositoryImpl): DURepository = duRepositoryImpl


}