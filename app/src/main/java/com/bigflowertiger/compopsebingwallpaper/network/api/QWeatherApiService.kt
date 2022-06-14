package com.bigflowertiger.compopsebingwallpaper.network.api

import com.bigflowertiger.compopsebingwallpaper.config.QWEATHER_KEY
import com.bigflowertiger.compopsebingwallpaper.data.model.qweather.*
import retrofit2.http.GET
import retrofit2.http.Query

interface QWeatherApiService {
    /**
     * 和风天气15天天气预报
     */
    @GET("/v7/weather/15d")
    suspend fun get15Days(
        @Query("location") location: String,
        @Query("key") key: String = QWEATHER_KEY,
        @Query("lang") lang: String = "zh",
        @Query("unit") unit: String = "m"
    ): QWDailyResponse

    /**
     * 和风天气24小时天气预报
     */
    @GET("/v7/weather/24h")
    suspend fun get24Hours(
        @Query("location") location: String,
        @Query("key") key: String = QWEATHER_KEY,
        @Query("lang") lang: String = "zh",
        @Query("unit") unit: String = "m"
    ): QWHourlyResponse

    /**
     * 和风天气实时天气。
     * 实况数据均为近实时数据，相比真实的物理世界会5-20分钟的延迟，请根据实况数据中的obsTime确定数据对应的准确时间。
     * @param lng 经度
     * @param lat 纬度
     * @param lang api返回结果使用的语言，默认中文
     * @param unit api返回结果使用的单位默认值m-公制。可选参数 m(公制)，i(英制)
     */
    @GET("/v7/weather/now")
    suspend fun getNow(
        @Query("location") location: String,
        @Query("key") key: String = QWEATHER_KEY,
        @Query("lang") lang: String = "zh",
        @Query("unit") unit: String = "m"
    ): QWNowResponse


    @GET("v7/air/now")
    suspend fun getAirNow(
        @Query("location") location: String,
        @Query("key") key: String = QWEATHER_KEY,
        @Query("lang") lang: String = "zh"
    ): QWAirNowResponse

    @GET("v7/air/5d")
    suspend fun getAirForecast(
        @Query("location") location: String,
        @Query("key") key: String = QWEATHER_KEY,
        @Query("lang") lang: String = "zh"
    ): QWAirForecastResponse

    @GET("/v7/warning/now")
    suspend fun getWarning(
        @Query("location") location: String,
        @Query("key") key: String = QWEATHER_KEY,
        @Query("lang") lang: String = "zh"
    ): QWWarningResponse

    @GET("/v7/indices/1d")
    suspend fun getLifeIndices(
        @Query("location") location: String,
        @Query("key") key: String = QWEATHER_KEY,
        @Query("type") type: String = "2,3,5,9",
        @Query("lang") lang: String = "zh"
    ): QWLifeIndicesResponse
}