package com.bigflowertiger.compopsebingwallpaper.data.model.qweather

import java.util.*

data class QWWarningResponse(
    val code: String,
    val updateTime: Date,
    val fxLink: String,
    val refer: Refer,
    val warning: List<QWWarning>
) {
    data class QWWarning(
        val id: String,
        val sender: String,
        val pubTime: Date,
        val title: String,
        val startTime: Date,
        val endTime: Date,
        val status: String,
        val level: String,
        val type: String,
        val typeName: String,
        val text: String,
        val related: String
    )

}
