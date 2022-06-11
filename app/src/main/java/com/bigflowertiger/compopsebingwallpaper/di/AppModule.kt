package com.bigflowertiger.compopsebingwallpaper.di

import com.bigflowertiger.compopsebingwallpaper.config.BASE_URL
import com.bigflowertiger.compopsebingwallpaper.domain.BingRepository
import com.bigflowertiger.compopsebingwallpaper.network.api.BingApiService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.hadiyarajesh.flower.calladpater.FlowCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideRetrofit(
        gson: Gson,
        flowCallAdapterFactory: FlowCallAdapterFactory
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addCallAdapterFactory(flowCallAdapterFactory)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideFlowCallAdapterFactory(): FlowCallAdapterFactory = FlowCallAdapterFactory.create()

    @Provides
    fun provideBingApiService(retrofit: Retrofit): BingApiService =
        retrofit.create(BingApiService::class.java)

    @Provides
    fun provideBingRepository(apiService: BingApiService) = BingRepository(apiService)

    @Provides
    fun provideDefaultDispatcher() = Dispatchers.Default
}