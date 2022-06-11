package com.bigflowertiger.compopsebingwallpaper.data.model

data class BingResponse(val images: List<BingItem>)

data class BingItem(
    var url: String = "",
    var urlbase: String = "",
    var title: String = "",
    var caption: String = "",
    var desc: String = "",
    var date: String = "",
    var copyright: String = "",
    var startdate: String = "",
    var enddate: String = "",
    var hsh: String = ""
)
