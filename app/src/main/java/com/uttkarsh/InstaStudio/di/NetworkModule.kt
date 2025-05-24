package com.uttkarsh.InstaStudio.di

import android.os.Build
import androidx.annotation.RequiresApi
import com.uttkarsh.InstaStudio.data.auth.AuthApiService
import com.uttkarsh.InstaStudio.data.auth.ProfileApiService
import com.uttkarsh.InstaStudio.utils.SharedPref.SessionStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.firstOrNull
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Provides
    @Singleton
    fun provideAuthInterceptor(sessionStore: SessionStore): Interceptor {
        return Interceptor { chain: Interceptor.Chain ->
            val requestBuilder = chain.request().newBuilder()

            val token = kotlinx.coroutines.runBlocking {
                sessionStore.accessTokenFlow.firstOrNull()
            }
            token?.let {
                requestBuilder.header("Authorization", "Bearer $it")
            }
            chain.proceed(requestBuilder.build())
        }
    }

    @Provides
    @Singleton
    @Named("UnauthenticatedClient")
    fun provideDefaultOkHttpClient(
        logging: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    @Provides
    @Singleton
    @Named("AuthenticatedClient")
    fun provideAuthOkHttpClient(
        logging: HttpLoggingInterceptor,
        authInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(logging)
            .build()
    }

    @Provides
    @Singleton
    @Named("UnauthenticatedRetrofit")
    fun provideRetrofit(
        @Named("UnauthenticatedClient") client: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://172.20.10.11:8080")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @Named("AuthenticatedRetrofit")
    fun provideAuthenticatedRetrofit(
        @Named("AuthenticatedClient") client: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://172.20.10.11:8080")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthApiService(
        @Named("UnauthenticatedRetrofit") retrofit: Retrofit
    ): AuthApiService {
        return retrofit.create(AuthApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideProfileApi(
        @Named("AuthenticatedRetrofit") retrofit: Retrofit
    ): ProfileApiService {
        return retrofit.create(ProfileApiService::class.java)
    }
}
