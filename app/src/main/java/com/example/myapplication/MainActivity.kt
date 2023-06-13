@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.myapplication

import android.Manifest
import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.twotone.LocationOn
import androidx.compose.material.icons.twotone.Person
import androidx.compose.material.icons.twotone.Place
import androidx.compose.material.icons.twotone.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.cart.viewModels.CartItemModelFactory
import com.example.myapplication.cart.viewModels.CartViewModel
import com.example.myapplication.map.views.MapView
import com.example.myapplication.profile.views.ProfileView
import com.example.myapplication.shop.viewModels.ShopItemModelFactory
import com.example.myapplication.shop.viewModels.ShopViewModel
import com.example.myapplication.shop.views.CartView
import com.example.myapplication.shop.views.ShopView
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.theme.Padding
import com.example.myapplication.weather.viewModels.WeatherModelFactory
import com.example.myapplication.weather.viewModels.WeatherViewModel
import com.example.myapplication.weather.views.WeatherView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch

data class User(
    val name: String,
    val title: String
)

data class NavigationItem(
    val icon: ImageVector,
    val name: String,
    val route: String
)

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface {
//                    if (ActivityCompat.checkSelfPermission(
//                            this,
//                            ACCESS_COARSE_LOCATION
//                        ) != PackageManager.PERMISSION_GRANTED
//                    ) {
//                        ActivityCompat.requestPermissions(
//                            this,
//                            arrayOf(ACCESS_COARSE_LOCATION),
//                            1
//                        )
//                    }

//                    val fusedLocationProviderClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

                    val navigationItems = listOf(
                        NavigationItem(
                            icon = Icons.TwoTone.ShoppingCart,
                            name = "Shop",
                            route = "shop"
                        ),
                        NavigationItem(
                            icon = Icons.TwoTone.Person,
                            name = "Profile",
                            route = "profile"
                        ),
                        NavigationItem(
                            icon = Icons.TwoTone.LocationOn,
                            name = "Weather",
                            route = "weather"
                        ),
                        NavigationItem(
                            icon = Icons.TwoTone.Place,
                            name = "Map",
                            route = "map"
                        )
                    )

                    val navController = rememberNavController()
                    val drawerState = rememberDrawerState(DrawerValue.Closed)
                    val scope = rememberCoroutineScope()
                    val selectedItem = remember { mutableStateOf(navigationItems[0]) }

                    val shopViewModel: ShopViewModel by viewModels {
                        ShopItemModelFactory((application as ShopApplication).shopItemRepository, (application as ShopApplication).cartItemRepository)
                    }

                    val cartViewModel: CartViewModel by viewModels {
                        CartItemModelFactory((application as ShopApplication).cartItemRepository)
                    }

                    val weatherViewModel: WeatherViewModel by viewModels {
                        WeatherModelFactory((application as ShopApplication).weatherRepository)
                    }

                    ModalNavigationDrawer(
                        drawerState = drawerState,
                        drawerContent = {
                            ModalDrawerSheet {
                                Spacer(Modifier.height(Padding.PaddingLarge.size))
                                navigationItems.forEach { item ->
                                    NavigationDrawerItem(
                                        icon = {
                                            Icon(
                                                item.icon,
                                                contentDescription = null,
                                                tint = MaterialTheme.colorScheme.primary
                                            )
                                        },
                                        label = { Text(item.name) },
                                        selected = item == selectedItem.value,
                                        onClick = {
                                            scope.launch { drawerState.close() }
                                            selectedItem.value = item
                                            navController.navigate(item.route)
                                        },
                                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                                    )
                                }
                            }
                        },
                        content = {
                            Scaffold(
                                topBar = {
                                    TopAppBar(
                                        title = {
                                            Row(
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Text(
                                                    text = selectedItem.value.name,
                                                    style = MaterialTheme.typography.titleLarge,
                                                    color = MaterialTheme.colorScheme.primary
                                                )
                                            }
                                        },
                                        navigationIcon = {
                                            IconButton(
                                                onClick = {
                                                    scope.launch { drawerState.open() }
                                                },
                                                modifier = Modifier
                                                    .padding(Padding.PaddingSmall.size)
                                            ) {
                                                Icon(
                                                    Icons.Default.Menu,
                                                    tint = MaterialTheme.colorScheme.primary,
                                                    contentDescription = null,
                                                    modifier = Modifier.size(64.dp)
                                                )
                                            }
                                        },
                                        actions = {
                                            IconButton(
                                                onClick = {
                                                    selectedItem.value = NavigationItem(
                                                        icon = Icons.TwoTone.ShoppingCart,
                                                        name = "Cart",
                                                        route = "cart"
                                                    )
                                                    navController.navigate("cart")
                                                },
                                                modifier = Modifier
                                                    .padding(Padding.PaddingSmall.size)
                                            ) {
                                                Icon(
                                                    Icons.TwoTone.ShoppingCart,
                                                    tint = MaterialTheme.colorScheme.primary,
                                                    contentDescription = null,
                                                    modifier = Modifier.size(64.dp)
                                                )
                                            }
                                        }
                                    )
                                },
                                content = { padding ->
                                    NavHost(navController = navController, startDestination = "shop") {
                                        composable("shop") {
                                            ShopView(
                                                viewModel = shopViewModel,
                                                modifier = Modifier.padding(padding)
                                            )
                                        }
                                        composable("profile") {
                                            ProfileView(
                                                user = User("John Doe", "Teacher"),
                                                modifier = Modifier.padding(padding)
                                            )
                                        }
                                        composable("cart") {
                                            CartView(
                                                viewModel = cartViewModel,
                                                modifier = Modifier.padding(padding)
                                            )
                                        }
                                        composable("weather") {
                                            WeatherView(
                                                viewModel = weatherViewModel,
                                                modifier = Modifier.padding(padding)
                                            )
                                        }
                                        composable("map") {
                                            MapView(
                                                modifier = Modifier.padding(padding)
                                            )
                                        }
                                    }
                                }
                            )
                        }
                    )
                }
            }
        }
    }
}

