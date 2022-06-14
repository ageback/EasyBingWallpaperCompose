package com.bigflowertiger.compopsebingwallpaper.domain

import com.bigflowertiger.compopsebingwallpaper.data.model.converter.QWeatherConverter
import com.bigflowertiger.compopsebingwallpaper.network.api.QWeatherApiService
import free.bigtiger.sunnyweatherarchitecture.data.model.generic.WeatherData
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QWeatherRepository
@Inject constructor(private val apiService: QWeatherApiService) {
    suspend fun get15Days(location: String) = apiService.get15Days(location)
    suspend fun get24Hours(location: String) = apiService.get24Hours(location)
    suspend fun getNow(location: String) = apiService.getNow(location)
    suspend fun getAirNow(location: String) = apiService.getAirNow(location)
    suspend fun getAirForecast(location: String) = apiService.getAirForecast(location)
    suspend fun getWarning(location: String) = apiService.getWarning(location)
    suspend fun getLifeIndices(location: String) = apiService.getLifeIndices(location)

    suspend fun combineQWeather(location: String): WeatherData {
        return coroutineScope {

            val dailyForecast = async { get15Days(location) }
            val hourlyForecast = async { get24Hours(location) }
            val now = async { getNow(location) }
            val airNow = async { getAirNow(location) }
            val airForecast = async { getAirForecast(location) }
            val warning = async { getWarning(location) }
            val lifeIndices = async { getLifeIndices(location) }

            QWeatherConverter.convertDaily(dailyForecast.await())
                .apply {
                    alert = QWeatherConverter.convertWarning(warning.await())
                    realtime = QWeatherConverter.convertRealtime(now.await())
                    realtime?.airQuality = QWeatherConverter.convertAirNow(airNow.await())
                    hourly = QWeatherConverter.convertHourly(hourlyForecast.await())
                    minutely = QWeatherConverter.convertHourlyToMinutely(hourlyForecast.await())
                    daily?.airQuality = QWeatherConverter.convertAirForecast(
                        daily?.airQuality!!,
                        airForecast.await()
                    )
                    daily.lifeIndex =
                        QWeatherConverter.convertLifeIndices(daily.lifeIndex, lifeIndices.await())
                    apiName = "和风天气"
                }
        }

    }

/*
    fun getWeatherRemote(location: Location): Flow<Resource<WeatherData>> {
        val loc = getLocationQueryParameter(location)
        val dailyFlow =
            apiCall(
                fetchRemote = {
                    apiService.get15Days(getLocationQueryParameter(location))
                },
                processData = {
                    if (it.code != 200) {
                        WeatherData(
                            errorMsg = QWErrorCode.getQWeatherMessage(it.code),
                            apiName = QWEATHER_NAME
                        )
                    } else {
                        QWeatherConverter.convertDaily(it)
                    }
                }
            )

        val nowFlow =
            apiCall(
                fetchRemote = { apiService.getNow(getLocationQueryParameter(location)) },
                processData = { it }
            )

        val airNowFlow =
            apiCall(
                fetchRemote = { apiService.getAirNow(getLocationQueryParameter(location)) },
                processData = { it }
            )

        val hourlyFlow =
            apiCall(
                fetchRemote = { apiService.get24Hours(getLocationQueryParameter(location)) },
                processData = { it }
            )

        val warningFlow =
            apiCall(
                fetchRemote = { apiService.getWarning(getLocationQueryParameter(location)) },
                processData = { it }
            )

        val airForecast =
            apiCall(
                fetchRemote = { apiService.getAirForecast(getLocationQueryParameter(location)) },
                processData = { it }
            )

        val lifeIndices =
            apiCall(
                fetchRemote = { apiService.getLifeIndices(getLocationQueryParameter(location)) },
                processData = { it }
            )


        return dailyFlow.combineTransform(nowFlow) { f1, f2 ->
            if (f1.status == Resource.Status.SUCCESS && f2.status == Resource.Status.SUCCESS) { // 合并实时天气数据
                if (f1.data!!.errorMsg?.isNotEmpty() == true) {
                    throw Exception(f1.data!!.errorMsg!!)
                }
                if (f2.data!!.code != 200) {
                    throw Exception(QWErrorCode.getQWeatherMessage(f2.data!!.code))
                }
                f1.data!!.realtime = QWeatherConverter.convertRealtime(f2.data!!)
                emit(Resource.success(f1.data))
            } else {
                emit(Resource.loading(null))
            }

        }.combineTransform(airNowFlow) { f1, f3 ->
            if (f1.status == Resource.Status.SUCCESS && f3.status == Resource.Status.SUCCESS) { // 合并实时空气质量数据
                f1.data!!.realtime!!.airQuality = QWeatherConverter.convertAirNow(f3.data!!)
                emit(Resource.success(f1.data))
            } else {
                emit(Resource.loading(null))
            }
        }.combineTransform(hourlyFlow) { f1, f4 ->
            if (f1.status == Resource.Status.SUCCESS && f4.status == Resource.Status.SUCCESS) { // 合并小时天气预报数据
                f1.data!!.hourly = QWeatherConverter.convertHourly(f4.data!!)
                f1.data!!.minutely = QWeatherConverter.convertHourlyToMinutely(f4.data!!)
                emit(Resource.success(f1.data))
            } else {
                emit(Resource.loading(null))
            }
        }.combineTransform(warningFlow) { f1, f5 ->
            if (f1.status == Resource.Status.SUCCESS && f5.status == Resource.Status.SUCCESS) {// 合并天气预警数据
                f1.data!!.alert = QWeatherConverter.convertWarning(f5.data!!)
                emit(Resource.success(f1.data))
            } else {
                emit(Resource.loading(null))
            }
        }.combineTransform(airForecast) { f1, f6 ->
            if (f1.status == Resource.Status.SUCCESS && f6.status == Resource.Status.SUCCESS) { // 合并天气预警数据
                f1.data!!.daily!!.airQuality = QWeatherConverter.convertAirForecast(
                    f1.data!!.daily!!.airQuality,
                    f6.data!!
                )
                emit(Resource.success(f1.data))
            } else {
                emit(Resource.loading(null))
            }
        }.combineTransform(lifeIndices) { f1, f7 ->
            if (f1.status == Resource.Status.SUCCESS && f7.status == Resource.Status.SUCCESS) { // 合并天气预警数据
                f1.data!!.daily!!.lifeIndex = QWeatherConverter.convertLifeIndices(
                    f1.data!!.daily!!.lifeIndex,
                    f7.data!!
                )
                emit(Resource.success(f1.data))
            } else {
                emit(Resource.loading(null))
            }
        }.catch {
            emit(Resource.error(msg = it.message!!, data = null))
        }.flowOn(Dispatchers.Main)
    }
*/

}