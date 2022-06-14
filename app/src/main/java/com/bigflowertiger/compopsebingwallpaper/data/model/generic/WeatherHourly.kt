package free.bigtiger.sunnyweatherarchitecture.data.model.generic

import com.google.gson.annotations.SerializedName
import java.util.*

data class WeatherHourly(
    val temperature: List<Temperature>,
    val skycon: List<Skycon>,
    val description: String,
    val precipitation: List<Precipitation>,
    val wind: List<Wind>,
    @SerializedName("air_quality") val airQuality: AirQualityHourly
) {
    data class Temperature(val datetime: Date, val value: Float)
    data class Skycon(val datetime: Date, val value: String, var text: String, var icon: Int)
    data class Precipitation(val datetime: Date, val value: Float)
    data class Wind(val datetime: Date, val speed: Float, val direction: Float)
    data class AirQualityHourly(val aqi: List<AqiHourly>, val pm25: List<Pm25Hourly>)
    data class AqiHourly(val datetime: Date, val value: AqiHourlyValue)
    data class AqiHourlyValue(val chn: Float, val usa: Float)
    data class Pm25Hourly(val datetime: Date, val value: Float)
}