package dev.mridx.delayed_uploader.di

import android.content.Context
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
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DelayedUploaderModule {



    /*@Provides
    @Singleton
    @DuRetrofit
    fun provideRetrofit(
        gson: Gson,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://google.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }*/


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