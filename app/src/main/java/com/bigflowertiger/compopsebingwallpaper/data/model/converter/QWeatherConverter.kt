package com.bigflowertiger.compopsebingwallpaper.data.model.converter

import com.bigflowertiger.compopsebingwallpaper.BingApp.Companion.context
import com.bigflowertiger.compopsebingwallpaper.data.model.converter.Functions.getElementList
import com.bigflowertiger.compopsebingwallpaper.data.model.qweather.*
import com.bigflowertiger.compopsebingwallpaper.di.QWEATHER_NAME
import free.bigtiger.sunnyweatherarchitecture.data.model.generic.*

object QWeatherConverter {
    fun convertDaily(qWeatherResponse: QWDailyResponse): WeatherData {

        val dailyList = qWeatherResponse.daily
        return WeatherData(
            alert = WeatherAlert(content = emptyList()),
            daily = WeatherDaily(
                astro = getElementList(dailyList) { daily ->
                    WeatherDaily.Astro(
                        sunrise = WeatherDaily.Sunrise(daily.sunrise),
                        sunset = WeatherDaily.Sunset(daily.sunset),
                        date = daily.fxDate
                    )
                },
                temperature = getElementList(dailyList) { daily ->
                    WeatherDaily.Temperature(
                        date = daily.fxDate,
                        max = daily.tempMax,
                        min = daily.tempMin,
                        avg = (daily.tempMax + daily.tempMin) / 2
                    )
                },
                skycon = getElementList(dailyList) {
                    WeatherDaily.Skycon(
                        value = it.iconDay,
                        date = it.fxDate,
                        text = it.textDay,
                        icon = convertIcon(it.iconDay)
                    )
                },
                skyconDay = getElementList(dailyList) {
                    WeatherDaily.SkyconDay(
                        value = it.iconDay,
                        date = it.fxDate,
                        text = it.textDay,
                        icon = convertIcon(it.iconDay)
                    )
                },
                skyconNight = getElementList(dailyList) { it ->
                    WeatherDaily.SkyconNight(
                        value = it.iconNight,
                        date = it.fxDate,
                        text = it.textDay,
                        icon = convertIcon(it.iconNight)
                    )
                },
                lifeIndex = WeatherDaily.LifeIndex(
                    coldRisk = getElementList(dailyList) { WeatherDaily.LifeDescription("") }.toMutableList(),
                    carWashing = getElementList(dailyList) { WeatherDaily.LifeDescription("") }.toMutableList(),
                    ultraviolet = getElementList(dailyList) { WeatherDaily.LifeDescription("") }.toMutableList(),
                    dressing = getElementList(dailyList) { WeatherDaily.LifeDescription("") }.toMutableList()
                ),
                precipitation = getElementList(dailyList) { daily ->
                    if (daily.precip != null) {
                        WeatherDaily.Precipitation(
                            date = daily.fxDate,
                            max = daily.precip,
                            min = daily.precip,
                            avg = daily.precip
                        )
                    } else {
                        WeatherDaily.Precipitation(
                            date = daily.fxDate,
                            max = 0f,
                            min = 0f,
                            avg = 0f
                        )
                    }
                },
                pressure = getElementList(dailyList) { daily ->
                    WeatherDaily.Pressure(
                        date = daily.fxDate,
                        max = daily.pressure * 100f,
                        min = daily.pressure * 100f,
                        avg = daily.pressure * 100f
                    )
                },
                wind = getElementList(dailyList) { daily ->
                    WeatherDaily.Wind(
                        date = daily.fxDate,
                        max = WeatherDaily.WindMax(
                            speed = daily.windSpeedDay,
                            direction = daily.wind360Day
                        ),

                        min = WeatherDaily.WindMin(
                            speed = daily.windSpeedDay,
                            direction = daily.wind360Day
                        ),
                        avg = WeatherDaily.WindAvg(
                            speed = daily.windSpeedDay,
                            direction = daily.wind360Day
                        )
                    )
                },
                humidity = getElementList(dailyList) { daily ->
                    WeatherDaily.Humidity(
                        max = daily.humidity / 100,
                        avg = daily.humidity / 100,
                        min = daily.humidity / 100
                    )
                },
                visibility = getElementList(dailyList) {
                    WeatherDaily.Visibility(
                        date = it.fxDate,
                        max = it.vis ?: 0f,
                        min = it.vis ?: 0f,
                        avg = it.vis ?: 0f
                    )
                },
                cloudrate = getElementList(dailyList) {
                    WeatherDaily.Cloudrate(
                        date = it.fxDate,
                        max = it.cloud ?: 0f,
                        min = it.cloud ?: 0f,
                        avg = it.cloud ?: 0f
                    )
                },
                airQuality = WeatherDaily.AirQualityDaily(
                    aqi = getElementList(dailyList) { daily ->
                        WeatherDaily.AQIDaily(
                            date = daily.fxDate,
                            max = WeatherDaily.AQIDailyMax(0f, 0f),
                            min = WeatherDaily.AQIDailyMin(0f, 0f),
                            avg = WeatherDaily.AQIDailyAvg(0f, 0f),
                        )
                    }.toMutableList(),
                    pm25 = getElementList(dailyList) { daily ->
                        WeatherDaily.PM25Daily(
                            date = daily.fxDate,
                            max = 0f,
                            min = 0f,
                            avg = 0f
                        )
                    }.toMutableList()
                )
            ),
            minutely = WeatherMinutely(probability = listOf(0f, 0f, 0f, 0f)),
            forecastKeypoint = "",
            realtime = null,
            hourly = null,
            apiName = QWEATHER_NAME
        )
    }

    private fun convertIcon(icon: String): Int =
        context.resources.getIdentifier(
            "qi_${icon}",
            "string",
            context.packageName
        )


    /**
     * 转换空气质量预报数据。由于和风天气免费个人开发版只提供5天的空气质量预报，但是天气预报有15天，这里只填充
     * 天气预报前5天的空气质量数据，剩下的天里面的空气质量保持初始值。
     */
    fun convertAirForecast(
        sourceAirQ: WeatherDaily.AirQualityDaily,
        airForecastResponse: QWAirForecastResponse
    ): WeatherDaily.AirQualityDaily {
        val airForecast = airForecastResponse.daily
        val aqi = getElementList(airForecast) {
            WeatherDaily.AQIDaily(
                date = it.fxDate,
                max = WeatherDaily.AQIDailyMax(it.aqi, it.aqi),
                min = WeatherDaily.AQIDailyMin(it.aqi, it.aqi),
                avg = WeatherDaily.AQIDailyAvg(it.aqi, it.aqi)
            )
        }
        val pm25 = getElementList(airForecast) { daily ->
            WeatherDaily.PM25Daily(
                date = daily.fxDate,
                max = 0f,
                min = 0f,
                avg = 0f
            )
        }
        airForecast.forEachIndexed { index, qwAirForecast ->
            sourceAirQ.aqi[index] = aqi[index]
            sourceAirQ.pm25[index] = pm25[index]
        }

        return sourceAirQ
    }

    fun convertLifeIndices(
        sourceLifeIndex: WeatherDaily.LifeIndex,
        lifeIndicesResponse: QWLifeIndicesResponse
    ): WeatherDaily.LifeIndex {
        sourceLifeIndex.apply {
            this.carWashing[0] =
                WeatherDaily.LifeDescription(
                    lifeIndicesResponse.daily.find { it.type == 2 }?.category ?: ""
                )
            this.dressing[0] =
                WeatherDaily.LifeDescription(
                    lifeIndicesResponse.daily.find { it.type == 3 }?.category ?: ""
                )
            this.ultraviolet[0] =
                WeatherDaily.LifeDescription(
                    lifeIndicesResponse.daily.find { it.type == 5 }?.category ?: ""
                )
            this.coldRisk[0] =
                WeatherDaily.LifeDescription(
                    lifeIndicesResponse.daily.find { it.type == 9 }?.category ?: ""
                )
        }
        return sourceLifeIndex
    }

    fun convertRealtime(nowResponse: QWNowResponse): Realtime {
        val now = nowResponse.now
        return Realtime(
            skycon = now.icon,
            skyText = now.text,
//            skyIcon = getSky(now.icon).icon,
            skyIcon = convertIcon(now.icon),
            temperature = now.temp,
            humidity = now.humidity / 100,
            wind = Realtime.Wind(
                speed = now.windSpeed,
                direction = now.wind360
            ),
            airQuality = Realtime.AirQuality(
                aqi = Realtime.AQI(0f, 0f),
                description = Realtime.Description("", "")
            ),
            apparentTemperature = now.feelsLike
        )
    }

    fun convertHourlyToMinutely(hourlyResponse: QWHourlyResponse): WeatherMinutely {
        val hourly = hourlyResponse.hourly
        return WeatherMinutely(
            probability = getElementList(hourly.slice(0..3)) {
                it.pop / 100
            }
        )
    }

    fun convertHourly(hourlyResponse: QWHourlyResponse): WeatherHourly {
        val hourly = hourlyResponse.hourly
        return WeatherHourly(
            temperature = getElementList(hourly) {
                WeatherHourly.Temperature(
                    value = it.temp,
                    datetime = it.fxTime
                )
            },
            skycon = getElementList(hourly) {
                WeatherHourly.Skycon(
                    datetime = it.fxTime,
                    value = it.icon,
                    text = it.text,
                    icon = convertIcon(it.icon)
                )
            },
            description = "",
            precipitation = getElementList(hourly) {
                WeatherHourly.Precipitation(
                    datetime = it.fxTime,
                    value = it.precip
                )
            },
            wind = getElementList(hourly) {
                WeatherHourly.Wind(
                    datetime = it.fxTime,
                    speed = it.windSpeed,
                    direction = it.wind360
                )
            },
            airQuality = WeatherHourly.AirQualityHourly(
                aqi = getElementList(hourly) {
                    WeatherHourly.AqiHourly(
                        datetime = it.fxTime,
                        WeatherHourly.AqiHourlyValue(
                            chn = 0f,
                            usa = 0f
                        )
                    )
                }.toMutableList(),
                pm25 = getElementList(hourly) {
                    WeatherHourly.Pm25Hourly(
                        datetime = it.fxTime,
                        value = 0f
                    )
                }.toMutableList()
            )
        )
    }

    fun convertWarning(warningResponse: QWWarningResponse): WeatherAlert {
        val warningList = warningResponse.warning
        return WeatherAlert(content = getElementList(warningList) {
            WeatherAlert.Content(
                status = it.status,
                request_status = "",
                pubtimestamp = 0L,
                publishTime = it.pubTime,
                alertId = it.id,
                adcode = "",
                location = "",
                province = "",
                city = "",
                county = "",
                code = it.type,
                source = it.sender,
                title = it.title,
                description = it.text,
                short = "${it.typeName}${it.level}预警"
            )
        })
    }


    fun convertAirNow(source: QWAirNowResponse): Realtime.AirQuality {
        val airNow = source.now

        return if (airNow != null) Realtime.AirQuality(
            aqi = Realtime.AQI(airNow.aqi, airNow.aqi),
            description = when (airNow.level) {
                1 -> Realtime.Description("优", "Excellent")
                2 -> Realtime.Description("良", "Good")
                3 -> Realtime.Description("中度污染", "Moderately Polluted ")
                4 -> Realtime.Description("重度污染", "Heavily Polluted")
                5 -> Realtime.Description("严重污染", "Severely Polluted")
                else -> Realtime.Description("缺数据", "No Data")
            }
        ) else
            Realtime.AirQuality(
                aqi = Realtime.AQI(0f, 0f),
                description = Realtime.Description("无数据", "No Data")
            )
    }


}
