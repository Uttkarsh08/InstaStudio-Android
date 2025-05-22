package com.uttkarsh.InstaStudio.di

import com.uttkarsh.InstaStudio.data.auth.AuthApiService
import com.uttkarsh.InstaStudio.data.auth.ProfileApiService
import com.uttkarsh.InstaStudio.utils.SharedPref.TokenStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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

    @Provides
    @Singleton
    fun provideAuthInterceptor(tokenStore: TokenStore): Interceptor {
        return Interceptor { chain ->
            val requestBuilder = chain.request().newBuilder()
            tokenStore.getAccessToken()?.let { token ->
                requestBuilder.header("Authorization", "Bearer $token")
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
