package free.bigtiger.sunnyweatherarchitecture.data.model.generic

import java.util.*

data class WeatherAlert(val content: List<Content>) {
    data class Content(
        val request_status: String,
        val pubtimestamp: Long,     // 发布时间，单位是 Unix 时间戳，如 1587443583
        val alertId: String,     // 预警ID，如 "35040041600001_20200421123203"
        val status: String,     // 预警信息的状态，如"预警中"
        val adcode: String,     // 区域代码，如 "350400"
        val location: String,     // 位置，如"福建省三明市"
        val province: String,     // 省，如"福建省"
        val city: String,     // 市，如"三明市"
        val county: String,     // 县，如"无"
        val code: String,     // 预警代码，如"0902"
        val source: String,     // 发布单位，如"国家预警信息发布中心",
        val title: String,     // 标题，如"三明市气象台发布雷电黄色预警[Ⅲ级/较重]",
        val description: String,     // 描述，如"三明市气象台2020年04月21日12时19分继续发布雷电黄色预警信号：预计未来6小时我市有雷电活动，局地伴有短时强降水、6-8级雷雨大风等强对流天气。请注意防范！"
//        var level: String,
        var short: String,
        var publishTime: Date
    )
}