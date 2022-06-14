package free.bigtiger.sunnyweatherarchitecture.data.model.generic

import com.google.gson.annotations.SerializedName

data class WeatherData(
    var alert: WeatherAlert? = null,
    var realtime: Realtime? = null,
    var hourly: WeatherHourly? = null,
    val daily: WeatherDaily? = null,
    var minutely: WeatherMinutely? = null,
    @SerializedName("forecast_keypoint") val forecastKeypoint: String = "",
    val errorMsg: String? = null,
    var apiName: String = "和风天气"
)
