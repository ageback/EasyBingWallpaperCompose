package com.bigflowertiger.compopsebingwallpaper.data.model.qweather

import java.util.*

data class QWLifeIndicesResponse(
    val code: String,
    val updateTime: Date,
    val fxLink: String,
    val daily: List<QWLifeIndices>,
    val refer: Refer
) {
    data class QWLifeIndices(
        val date: Date,
        val type: Int, // 数据字典： https://dev.qweather.com/docs/resource/indices-info/
        val name: String,
        val level: Int,
        val category: String,
        val text: String
    )
}
