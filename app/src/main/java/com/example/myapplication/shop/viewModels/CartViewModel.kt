package com.example.myapplication.cart.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.myapplication.cart.models.cartItem.CartItemRepository
import com.example.myapplication.shop.models.cartItem.CartItem
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.Flow

class CartViewModel(
    private val repository: CartItemRepository,
): ViewModel() {

    var cartItems: Flow<List<CartItem>> = repository.allCartItems

    fun addCartItem(newCartItem: CartItem) = viewModelScope.launch {
        repository.insertCartItem(newCartItem)
    }

    fun updateCartItem(newCartItem: CartItem) = viewModelScope.launch {
        repository.updateCartItem(newCartItem)
    }

    fun deleteCartItem(newCartItem: CartItem) = viewModelScope.launch {
        repository.deleteCartItem(newCartItem)
    }
}

class CartItemModelFactory(
    private val repository: CartItemRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CartViewModel::class.java))
            return CartViewModel(repository) as T
        throw IllegalArgumentException("Error")
    }
}