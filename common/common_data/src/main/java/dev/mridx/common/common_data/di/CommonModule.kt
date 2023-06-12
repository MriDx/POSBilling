package dev.mridx.common.common_data.di

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.undabot.izzy.parser.GsonParser
import com.undabot.izzy.parser.IzzyConfiguration
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.mridx.common.common_data.data.constants.ACCESS_TOKEN
import dev.mridx.common.common_data.data.constants.APP_SHARED_PREF
import dev.mridx.common.common_data.data.constants.PERMISSION_SHARED_PREF
import dev.mridx.common.common_data.di.qualifier.ApiBaseUrl
import dev.mridx.common.common_data.di.qualifier.AppPreference
import dev.mridx.common.common_data.di.qualifier.AuthInterceptor
import dev.mridx.common.common_data.di.qualifier.BaseUrl
import dev.mridx.common.common_data.di.qualifier.CommonInterceptor
import dev.mridx.common.common_data.di.qualifier.PermissionPreference
import dev.mridx.common.common_data.izzy_parser_wrapper.MyIzzy
import dev.mridx.common.common_data.izzy_parser_wrapper.MyIzzyConverter
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object CommonModule {


    @Provides
    @Singleton
    @BaseUrl
    fun provideBaseUrl(): String =
        if (true) "http://jjm.sumato.tech/" else "http://jjm.sumato.tech/"
    //if (BuildConfig.DEBUG) "https://adcb-2405-201-a802-1833-35c0-2ab2-87d0-f7bb.ngrok-free.app/" else "https://irrigationassam.in/"


    @Provides
    @Singleton
    @ApiBaseUrl
    fun provideApiBaseUrl(@BaseUrl baseUrl: String): String {
        return "${baseUrl}api/"
    }


    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .create()
    }

    @Provides
    @Singleton
    fun provideGsonParser(gson: Gson): GsonParser {
        return GsonParser(
            izzyConfiguration = IzzyConfiguration(
                resourceTypes = arrayOf(
                )
            ),
            gson = gson
        )
    }

    @Provides
    @Singleton
    fun provideAnnotatedConverter(
        gson: Gson,
        gsonParser: GsonParser,
    ): AnnotatedConverter {
        return AnnotatedConverter(
            factoryMap = mapOf(
                GsonInterface::class.java to GsonConverterFactory.create(gson),
                IzzyInterface::class.java to MyIzzyConverter(izzy = MyIzzy(izzyJsonParser = gsonParser)),
            )
        )
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }


    @Singleton
    @Provides
    @AuthInterceptor
    fun provideAuthInterceptor(@AppPreference sharedPreferences: SharedPreferences): Interceptor {
        return Interceptor { chain ->
            val token = sharedPreferences.getString(ACCESS_TOKEN, "") ?: ""
            val request = chain.request()
            val authRequest = request.newBuilder()
                .addHeader("Authorization", token)
                .build()
            chain.proceed(authRequest)
        }
    }

    @Singleton
    @Provides
    @CommonInterceptor
    fun provideCommonInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()
            val commonRequest = request.newBuilder()
                .addHeader("Accept", "application/json")
                .build()
            chain.proceed(commonRequest)
        }
    }
/*

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        @AuthInterceptor authInterceptor: Interceptor,
        @CommonInterceptor commonInterceptor: Interceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder().apply {
            if (true)
                addInterceptor(loggingInterceptor)
            addInterceptor(authInterceptor)
            addInterceptor(commonInterceptor)
            callTimeout(600, TimeUnit.SECONDS)
            readTimeout(600, TimeUnit.SECONDS)
            connectTimeout(10000, TimeUnit.SECONDS)
            build()
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
*/


    @Singleton
    @Provides
    @AppPreference
    fun provideAppSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(APP_SHARED_PREF, Context.MODE_PRIVATE)


    @Singleton
    @Provides
    @PermissionPreference
    fun providePermissionPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(PERMISSION_SHARED_PREF, Context.MODE_PRIVATE)


}