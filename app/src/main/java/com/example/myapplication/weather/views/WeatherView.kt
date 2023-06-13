package com.example.myapplication.weather.views

import android.Manifest
import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.annotation.SuppressLint
import android.app.Application
import android.content.pm.PackageManager
import android.location.Location
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import com.example.myapplication.ui.theme.Padding
import com.example.myapplication.weather.models.WeatherResponse
import com.example.myapplication.weather.models.emptyWeatherModelResponse
import com.example.myapplication.weather.viewModels.WeatherViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlin.coroutines.coroutineContext

@SuppressLint("MissingPermission")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherView(
    viewModel: WeatherViewModel,
    modifier: Modifier = Modifier
    ) {
    var latitude by remember { mutableStateOf(TextFieldValue("0")) }
    var longitude by remember { mutableStateOf(TextFieldValue("0")) }


    val weatherResponse: WeatherResponse by viewModel.getCurrentWeather(
        latitude = latitude.text,
        longitude = longitude.text,
        currentWeather = "true"
    ).collectAsStateWithLifecycle(initialValue = emptyWeatherModelResponse)

    Box(
        modifier = modifier
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
            ),
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier.padding(Padding.PaddingMedium.size)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Padding.PaddingMedium.size)
            ) {

                TextField(
                    value = latitude,
                    onValueChange = {
                        latitude = it
                    },
                    label = { Text(text = "Latitude") },
                    placeholder = { Text(text = "Latitude of current location") },
                    modifier = Modifier.padding(Padding.PaddingSmall.size)
                )
                TextField(
                    value = longitude,
                    onValueChange = {
                        longitude = it
                    },
                    label = { Text(text = "Longitude") },
                    placeholder = { Text(text = "Longitude of current location") },
                    modifier = Modifier.padding(Padding.PaddingSmall.size),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Padding.PaddingMedium.size)
            ) {
                Text(
                    text = "Current Weather:\n ${weatherResponse.currentWeather.temperature} °C",
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}