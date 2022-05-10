package com.dzenlab.adviceandinsult.di

import android.content.Context
import com.dzenlab.adviceandinsult.data.constants.*
import com.dzenlab.adviceandinsult.data.network.storage.AdviceNetworkStorage
import com.dzenlab.adviceandinsult.data.network.storage.InsultNetworkStorage
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideAdviceNetworkStorage(retrofit: Retrofit): AdviceNetworkStorage =
        AdviceNetworkStorage(retrofit)

    @Provides
    @Singleton
    fun provideInsultNetworkStorage(retrofit: Retrofit): InsultNetworkStorage =
        InsultNetworkStorage(retrofit)

    @Provides
    @Singleton
    fun provideRetrofit(gsonConverterFactory: GsonConverterFactory,
                        okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(gsonConverterFactory)
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory =
        GsonConverterFactory.create(GsonBuilder().setDateFormat(DATE_FORMAT).create())

    @Provides
    @Singleton
    fun provideOkHttpClient(@ApplicationContext context: Context): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIMEOUT_SECOND, TimeUnit.SECONDS)
            .readTimeout(READ_WRITE_TIMEOUT_SECOND, TimeUnit.SECONDS)
            .writeTimeout(READ_WRITE_TIMEOUT_SECOND, TimeUnit.SECONDS)
            .cache(Cache(context.cacheDir, CACHE_SIZE))
            .addInterceptor {chain ->
                var request = chain.request()
                val builder = request.headers().newBuilder()
                builder.add(CACHE_CONTROL_HEADER, CACHE_CONTROL_VALUE)
                builder.add(CONTENT_TYPE_HEADER, CONTENT_TYPE_APP_JSON)
                request = request.newBuilder().headers(builder.build()).build()
                chain.proceed(request)
            }.build()
}