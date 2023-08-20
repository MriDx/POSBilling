package dev.techmess.gym_mem.di

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.core.os.bundleOf
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.mridx.common.common_data.di.AnnotatedConverter
import dev.mridx.common.common_data.di.qualifier.ApiBaseUrl
import dev.mridx.common.common_data.di.qualifier.AppPreference
import dev.mridx.common.common_data.di.qualifier.AuthInterceptor
import dev.mridx.common.common_data.di.qualifier.CommonInterceptor
import dev.mridx.common.common_data.di.qualifier.ResponseInterceptor
import dev.techmess.gym_mem.BuildConfig
import dev.techmess.gym_mem.presentation.auth.activity.AuthActivity
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton



@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Singleton
    @Provides
    @ResponseInterceptor
    fun provideResponseInterceptor(
        @ApplicationContext context: Context,
        @AppPreference appSharedPreferences: SharedPreferences
    ): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()
            val response = chain.proceed(request)
            if (response.code == 401) {
                handleUnAuthResponse(context, appSharedPreferences)
            }
            response
        }
    }

    private fun handleUnAuthResponse(context: Context, appSharedPreferences: SharedPreferences) {
        appSharedPreferences.edit {
            clear()
        }
        val i = Intent(context, AuthActivity::class.java)
        i.putExtras(
            bundleOf(
                "message" to "You are currently logged out. Please login to proceed."
            )
        )
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK + Intent.FLAG_ACTIVITY_CLEAR_TASK
        context.startActivity(i)
    }


    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        @AuthInterceptor authInterceptor: Interceptor,
        @CommonInterceptor commonInterceptor: Interceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG)
                addInterceptor(loggingInterceptor)
            addInterceptor(authInterceptor)
            addInterceptor(commonInterceptor)
            callTimeout(600, TimeUnit.SECONDS)
            readTimeout(600, TimeUnit.SECONDS)
            connectTimeout(10000, TimeUnit.SECONDS)
        }.build()

    }


    @Provides
    @Singleton
    fun provideRetrofit(
        @ApiBaseUrl apiBaseUrl: String,
        okHttpClient: OkHttpClient,
        annotatedConverter: AnnotatedConverter,
    ): Retrofit {
        return Retrofit.Builder().apply {
            baseUrl(apiBaseUrl)
            client(okHttpClient)
            addConverterFactory(annotatedConverter)
        }.build()
    }


}