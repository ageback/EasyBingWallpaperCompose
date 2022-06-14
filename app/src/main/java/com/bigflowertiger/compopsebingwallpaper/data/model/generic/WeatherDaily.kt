package free.bigtiger.sunnyweatherarchitecture.data.model.generic

import com.google.gson.annotations.SerializedName
import java.util.*

data class WeatherDaily(
    val astro: List<Astro>,
    val temperature: List<Temperature>,
    val skycon: List<Skycon>,
    @SerializedName("skycon_08h_20h") val skyconDay: List<SkyconDay>,
    @SerializedName("skycon_20h_32h") val skyconNight: List<SkyconNight>,
    @SerializedName("life_index") var lifeIndex: LifeIndex,
    val precipitation: List<Precipitation>,
    val pressure: List<Pressure>,
    val wind: List<Wind>,
    val humidity: List<Humidity>,
    val visibility: List<Visibility>,
    val cloudrate: List<Cloudrate>,
    @SerializedName("air_quality") var airQuality: AirQualityDaily
) {
    data class Wind(val date: Date, val max: WindMax, val min: WindMin, val avg: WindAvg)

    data class Astro(val date: Date, val sunrise: Sunrise, val sunset: Sunset)
    data class Sunrise(val time: String)
    data class Sunset(val time: String)

    data class Temperature(val date: Date, val max: Float, val min: Float, val avg: Float)
    data class Skycon(val value: String, val date: Date, var text: String, var icon: Int)
    data class SkyconDay(val value: String, val date: Date, var text: String, var icon: Int)
    data class SkyconNight(val value: String, val date: Date, var text: String, var icon: Int)
    data class LifeIndex(
        val coldRisk:MutableList<LifeDescription>,
        val carWashing:MutableList<LifeDescription>,
        val ultraviolet:MutableList<LifeDescription>,
        val dressing:MutableList<LifeDescription>
    )

    data class LifeDescription(val desc: String)
    data class Precipitation(val date: Date, val max: Float, val min: Float, val avg: Float)
    data class Pressure(val date: Date, val max: Float, val min: Float, val avg: Float)
    data class Visibility(val date: Date, val max: Float, val min: Float, val avg: Float)
    data class Cloudrate(val date: Date, val max: Float, val min: Float, val avg: Float)

    data class WindMax(val speed: Float, val direction: Float)
    data class WindMin(val speed: Float, val direction: Float)
    data class WindAvg(val speed: Float, val direction: Float)

    data class Humidity(val max: Float, val min: Float, val avg: Float)

    data class AirQualityDaily(var aqi: MutableList<AQIDaily>, val pm25: MutableList<PM25Daily>)

    data class AQIDaily(
        val date: Date,
        val max: AQIDailyMax,
        val min: AQIDailyMin,
        val avg: AQIDailyAvg
    )

    data class AQIDailyMax(val chn: Float, val usa: Float)
    data class AQIDailyMin(val chn: Float, val usa: Float)
    data class AQIDailyAvg(val chn: Float, val usa: Float)

    data class PM25Daily(val date: Date, val max: Float, val min: Float, val avg: Float)
}
