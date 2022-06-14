package com.bigflowertiger.compopsebingwallpaper.data.model.qweather

import java.util.*

data class QWNowResponse(
    val code: Int,
    val updateTime: Date,
    val fxLink: String,
    val now: QWNow,
    val refer: Refer
) {
    data class QWNow(
        val obsTime: Date,
        val temp: Float,
        val feelsLike: Float,
        val icon: String,
        val text: String,
        val wind360: Float,
        val windDir: String,
        val windScale: String,
        val windSpeed: Float,
        val humidity: Float,
        val precip: Float,
        val pressure: Float,
        val vis: Float,
        val cloud: String,
        val dew: Float,
    )
}
