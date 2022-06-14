package com.bigflowertiger.compopsebingwallpaper.data.model.qweather

import java.util.*

data class QWHourlyResponse(
    val code: String,
    val updateTime: Date,
    val fxLink: String,
    val hourly: List<QWHourly>,
    val refer: Refer
) {
    data class QWHourly(
        val fxTime: Date,
        val temp: Float,
        val icon: String,
        val text: String,
        val wind360: Float,
        val windDir: String,
        val windScale: String,
        val windSpeed: Float,
        val humidity: String,
        val pop: Float,
        val precip: Float,
        val pressure: String,
        val cloud: String,
        val dew: String
    )
}
