package com.bigflowertiger.compopsebingwallpaper.data.model.qweather

import java.util.*

data class QWAirForecastResponse(
    val code: String,
    val updateTime: Date,
    val fxLink: String,
    val daily: List<QWAirForecast>,
    val refer: Refer
) {
    data class QWAirForecast(
        val fxDate: Date,
        val aqi: Float,
        val level: Int,
        val category: String,
        val primary: String
    )
}