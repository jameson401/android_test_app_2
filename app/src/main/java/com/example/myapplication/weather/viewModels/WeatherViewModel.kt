package com.example.myapplication.weather.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.cart.models.cartItem.WeatherRepository
import com.example.myapplication.weather.models.WeatherResponse
import kotlinx.coroutines.flow.Flow


class WeatherViewModel(
    private val repository: WeatherRepository = WeatherRepository()
): ViewModel() {
    fun getCurrentWeather(
        latitude: String,
        longitude: String,
        currentWeather: String
    ): Flow<WeatherResponse> {
        return repository.fetchWeather(latitude, longitude, currentWeather)
    }
}

class WeatherModelFactory(
    private val repository: WeatherRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java))
            return WeatherViewModel(repository) as T
        throw IllegalArgumentException("Error")
    }
}