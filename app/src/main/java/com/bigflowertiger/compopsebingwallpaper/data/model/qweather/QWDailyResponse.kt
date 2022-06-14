package com.bigflowertiger.compopsebingwallpaper.data.model.qweather

import java.util.*

data class QWDailyResponse(
    val code: Int,
    val updateTime: Date,
    val fxLink: String,
    val daily: List<QWDaily>,
    val refer: Refer
) {
    data class QWDaily(
        val fxDate: Date,
        val sunrise: String,
        val sunset: String,
        val moonrise: String,
        val moonset: String,
        val moonPhase: String,
        val moonPhaseIcon: String,
        val tempMax: Float,
        val tempMin: Float,
        val iconDay: String,
        val textDay: String,
        val iconNight: String,
        val textNight: String,
        val wind360Day: Float,
        val windDirDay: String,
        val windScaleDay: String,
        val windSpeedDay: Float,
        val wind360Night: String,
        val windDirNight: String,
        val windScaleNight: String,
        val windSpeedNight: Float,
        val humidity: Float,
        val precip: Float,
        val pressure: Int,
        val vis: Float?,
        val cloud: Float?,
        val uvIndex: String?
    )
}
