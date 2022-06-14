package com.bigflowertiger.compopsebingwallpaper.data.model.qweather

import java.util.*

data class QWAirNowResponse(
    val code: String,
    val updateTime: Date,
    val fxLink: String,
    val now: QWAirNow,
    val refer: Refer
) {
    data class QWAirNow(
        val pubTime: Date,
        val aqi: Float,
        val level: Int,
        val category: String,
        val primary: String,
        val pm10: Int,
        val pm2p5: Int,
        val no2: Int,
        val so2: Int,
        val co: Float,
        val o3: Int
    )
}
