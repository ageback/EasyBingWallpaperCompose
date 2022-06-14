package com.bigflowertiger.compopsebingwallpaper.di

import com.bigflowertiger.compopsebingwallpaper.config.BASE_URL
import com.bigflowertiger.compopsebingwallpaper.config.QWEATHER_BASE_URL
import com.bigflowertiger.compopsebingwallpaper.domain.BingRepository
import com.bigflowertiger.compopsebingwallpaper.domain.QWeatherRepository
import com.bigflowertiger.compopsebingwallpaper.network.api.BingApiService
import com.bigflowertiger.compopsebingwallpaper.network.api.QWeatherApiService
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
import javax.inject.Named
import javax.inject.Singleton

const val BING_NAME = "bing"
const val QWEATHER_NAME = "qweather"

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    @Named(BING_NAME)
    fun provideBingRetrofit(
        gson: Gson,
        flowCallAdapterFactory: FlowCallAdapterFactory
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addCallAdapterFactory(flowCallAdapterFactory)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Singleton
    @Provides
    @Named(QWEATHER_NAME)
    fun provideQWeatherRetrofit(
        gson: Gson,
        flowCallAdapterFactory: FlowCallAdapterFactory
    ): Retrofit = Retrofit.Builder()
        .baseUrl(QWEATHER_BASE_URL)
        .addCallAdapterFactory(flowCallAdapterFactory)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideFlowCallAdapterFactory(): FlowCallAdapterFactory = FlowCallAdapterFactory.create()

    @Provides
    fun provideQWeatherService(@Named(QWEATHER_NAME) retrofit: Retrofit): QWeatherApiService =
        retrofit.create(QWeatherApiService::class.java)

    @Provides
    fun provideBingApiService(@Named(BING_NAME) retrofit: Retrofit): BingApiService =
        retrofit.create(BingApiService::class.java)

    @Provides
    fun provideBingRepository(apiService: BingApiService) = BingRepository(apiService)

    @Provides
    fun provideQWeatherRepository(apiService: QWeatherApiService) = QWeatherRepository(apiService)

    @Provides
    fun provideDefaultDispatcher() = Dispatchers.Default
}