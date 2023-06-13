package com.example.myapplication.weather.models.remote

import com.example.myapplication.weather.models.WeatherResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

object WeatherAPIClient {
    private val BASE_URL = "https://api.open-meteo.com/v1/"

    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    val weatherAPIService: WeatherAPIService by lazy {
        retrofit.create(WeatherAPIService::class.java)
    }
}

interface WeatherAPIService {
    @GET("forecast")
    suspend fun fetchWeather(
        @Query("latitude")
        latitude: String,
        @Query("longitude")
        longitude: String,
        @Query("current_weather")
        currentWeather: String
    ): Response<WeatherResponse>
}

interface WeatherAPIHelper {
    fun fetchWeather(
        latitude: String,
        longitude: String,
        currentWeather: String
    ): Flow<WeatherResponse>
}

/** TODO: Add in sealed class for network response
 *
 * This code is currently unused
 *
 * */
enum class ApiStatus{
    SUCCESS,
    ERROR,
    LOADING
}

sealed class ApiResult <out T> (val status: ApiStatus, val data: T?, val message:String?) {

    data class Success<out R>(val _data: R?): ApiResult<R>(
        status = ApiStatus.SUCCESS,
        data = _data,
        message = null
    )

    data class Error(val exception: String): ApiResult<Nothing>(
        status = ApiStatus.ERROR,
        data = null,
        message = exception
    )

    data class Loading<out R>(val _data: R?, val isLoading: Boolean): ApiResult<R>(
        status = ApiStatus.LOADING,
        data = _data,
        message = null
    )
}