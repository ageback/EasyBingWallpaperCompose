package free.bigtiger.sunnyweatherarchitecture.data.model.generic

import com.google.gson.annotations.SerializedName

data class Realtime(
    val skycon: String,
    var skyText: String,
    var skyIcon: Int,
    val temperature: Float,
    val humidity: Float,
    val wind: Wind,
    @SerializedName("air_quality") var airQuality: AirQuality,
    @SerializedName("apparent_temperature") val apparentTemperature: Float // 体感温度
) {
    data class Wind(val speed: Float, val direction: Float)

    data class AirQuality(val aqi: AQI, val description: Description)

    data class AQI(val chn: Float, val usa: Float)
    data class Description(val chn: String, val usa: String)
}
