package com.example.myapplication.map.views

import android.content.res.Resources
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.core.content.res.TypedArrayUtils.getString
import androidx.core.content.res.TypedArrayUtils.getText
import com.example.myapplication.R
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.GroundOverlay
import com.google.maps.android.compose.GroundOverlayPosition
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MapView(
    modifier: Modifier = Modifier
) {
    val singapore = LatLng(1.35, 103.87)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(singapore, 10f)
    }

    var uiSettings = remember { mutableStateOf(MapUiSettings(
        compassEnabled = true,
        zoomControlsEnabled = false
    )) }

    val mapProperties = MapProperties(mapStyleOptions = MapStyleOptions("[\n" +
            "    {\n" +
            "        \"featureType\": \"all\",\n" +
            "        \"elementType\": \"geometry\",\n" +
            "        \"stylers\": [\n" +
            "            {\n" +
            "                \"visibility\": \"off\"\n" +
            "            }\n" +
            "        ]\n" +
            "    },\n" +
            "    {\n" +
            "        \"featureType\": \"all\",\n" +
            "        \"elementType\": \"labels\",\n" +
            "        \"stylers\": [\n" +
            "            {\n" +
            "                \"visibility\": \"off\"\n" +
            "            }\n" +
            "        ]\n" +
            "    },\n" +
            "    {\n" +
            "        \"featureType\": \"landscape.natural\",\n" +
            "        \"elementType\": \"geometry\",\n" +
            "        \"stylers\": [\n" +
            "            {\n" +
            "                \"visibility\": \"on\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"color\": \"#ffffff\"\n" +
            "            }\n" +
            "        ]\n" +
            "    },\n" +
            "    {\n" +
            "        \"featureType\": \"water\",\n" +
            "        \"elementType\": \"all\",\n" +
            "        \"stylers\": [\n" +
            "            {\n" +
            "                \"visibility\": \"on\"\n" +
            "            }\n" +
            "        ]\n" +
            "    },\n" +
            "    {\n" +
            "        \"featureType\": \"water\",\n" +
            "        \"elementType\": \"labels\",\n" +
            "        \"stylers\": [\n" +
            "            {\n" +
            "                \"visibility\": \"off\"\n" +
            "            }\n" +
            "        ]\n" +
            "    }\n" +
            "]"))

    val mapOptions = GoogleMapOptions()
        .mapId("95293be67155c6a4")
        .scrollGesturesEnabled(false)

    GoogleMap(
        properties = mapProperties,
        uiSettings = uiSettings.value,
        cameraPositionState = cameraPositionState,
        modifier = Modifier.fillMaxSize()
    ) {

//        GroundOverlay(
//            position = GroundOverlayPosition.create(
//                latLngBounds = LatLngBounds(
//                    LatLng(1.35,103.87),
//                    LatLng(10.10,113.87))
//            ),
//            image = BitmapDescriptorFactory.defaultMarker()
//        )
        Marker(
            state = MarkerState(position = singapore),
            title = "Singapore",
            snippet = "Marker in Singapore"
        )
    }
}