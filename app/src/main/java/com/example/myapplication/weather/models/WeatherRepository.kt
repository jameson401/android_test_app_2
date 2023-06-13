package com.example.myapplication.cart.models.cartItem

import androidx.annotation.WorkerThread
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.myapplication.shop.models.cartItem.CartItem
import com.example.myapplication.weather.models.WeatherModel
import com.example.myapplication.weather.models.WeatherResponse
import com.example.myapplication.weather.models.emptyWeatherModelResponse
import com.example.myapplication.weather.models.remote.WeatherAPIClient
import com.example.myapplication.weather.models.remote.WeatherAPIHelper
import com.example.myapplication.weather.models.remote.WeatherAPIService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import retrofit2.http.Query

class WeatherRepository: WeatherAPIHelper {
    override fun fetchWeather(
        latitude: String,
        longitude: String,
        currentWeather: String
    ): Flow<WeatherResponse> = flow {
        val response = WeatherAPIClient.weatherAPIService.fetchWeather(
            latitude, longitude, currentWeather
        )
        if(response.isSuccessful) {
            emit(response.body()!!)
        } else {
            emit(emptyWeatherModelResponse)
        }
    }
}