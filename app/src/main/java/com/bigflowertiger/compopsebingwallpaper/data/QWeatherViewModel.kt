package com.bigflowertiger.compopsebingwallpaper.data

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bigflowertiger.compopsebingwallpaper.domain.QWeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import free.bigtiger.sunnyweatherarchitecture.data.model.generic.WeatherData
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QWeatherViewModel @Inject constructor(
    private val qWeatherRepository: QWeatherRepository
) : ViewModel() {
    private val _weatherState: MutableState<WeatherData> = mutableStateOf(WeatherData())
    val weatherState: State<WeatherData> = _weatherState

    init {
        fetchQWeather("120.173580,31.384260")
    }

    private fun fetchQWeather(location: String) {
        viewModelScope.launch {
            try {
                Log.d("qweather", weatherState.toString())
                _weatherState.value = qWeatherRepository.combineQWeather(location)
            } catch (e: Exception) {
            }
        }

    }

}