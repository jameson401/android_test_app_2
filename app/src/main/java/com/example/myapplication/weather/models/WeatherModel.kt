package com.example.myapplication.weather.models

import com.squareup.moshi.Json
import retrofit2.Response

data class WeatherModel(
    @Json(name = "temperature")
    var temperature: Double,
    @Json(name = "windspeed")
    var windSpeed: Double,
    @Json(name = "winddirection")
    var windDirection: Double,
    @Json(name = "weathercode")
    var weatherCode: Int,
    @Json(name = "is_day")
    var isDay: Int,
    @Json(name = "time")
    var time: String
)

data class WeatherResponse(
    @Json(name = "current_weather")
    var currentWeather: WeatherModel
)

val emptyWeatherModelResponse = WeatherResponse(
    WeatherModel(
        0.0,
        0.0,
        0.0,
    0,
        0,
        ""
    )
)