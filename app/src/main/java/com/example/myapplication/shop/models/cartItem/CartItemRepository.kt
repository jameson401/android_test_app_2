package com.example.myapplication.cart.models.cartItem

import androidx.annotation.WorkerThread
import com.example.myapplication.shop.models.cartItem.CartItem
import kotlinx.coroutines.flow.Flow

class CartItemRepository(
    private val cartItemDao: CartItemDao
) {
    val allCartItems: Flow<List<CartItem>> = cartItemDao.allCartItems()

    @WorkerThread
    suspend fun insertCartItem(cartItem: CartItem) {
        cartItemDao.insertCartItem(cartItem)
    }

    @WorkerThread
    suspend fun updateCartItem(cartItem: CartItem) {
        cartItemDao.updateCartItem(cartItem)
    }

    @WorkerThread
    suspend fun deleteCartItem(cartItem: CartItem) {
        cartItemDao.deleteCartItem(cartItem)
    }
}