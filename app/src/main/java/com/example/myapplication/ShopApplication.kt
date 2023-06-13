package com.example.myapplication

import android.app.Application
import com.example.myapplication.cart.models.cartItem.CartItemDatabase
import com.example.myapplication.cart.models.cartItem.CartItemRepository
import com.example.myapplication.cart.models.cartItem.WeatherRepository
import com.example.myapplication.shop.models.shopItem.ShopItemDatabase
import com.example.myapplication.shop.models.shopItem.ShopItemRepository

class ShopApplication: Application() {
    private val shopItemDatabase by lazy { ShopItemDatabase.getDatabase(this) }
    val shopItemRepository by lazy { ShopItemRepository(shopItemDatabase.shopItemDao()) }

    private val cartItemDatabase by lazy { CartItemDatabase.getDatabase(this) }
    val cartItemRepository by lazy { CartItemRepository(cartItemDatabase.cartItemDao()) }

    val weatherRepository by lazy { WeatherRepository() }
}