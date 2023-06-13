package com.example.myapplication.shop.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.myapplication.cart.models.cartItem.CartItemRepository
import com.example.myapplication.shop.models.cartItem.CartItem
import com.example.myapplication.shop.models.shopItem.ShopItem
import com.example.myapplication.shop.models.shopItem.ShopItemRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.Flow

class ShopViewModel(
    private val repository: ShopItemRepository,
    private val cartItemRepository: CartItemRepository
): ViewModel() {
    var shopItems: Flow<List<ShopItem>> = repository.allShopItems

    fun addShopItem(newShopItem: ShopItem) = viewModelScope.launch {
        repository.insertShopItem(newShopItem)
    }

    fun updateShopItem(shopItem: ShopItem) = viewModelScope.launch {
        repository.updateShopItem(shopItem)
    }

    fun deleteShopItem(shopItem: ShopItem) = viewModelScope.launch {
        repository.deleteShopItem(shopItem)
    }

    fun addCartItem(newCartItem: CartItem) = viewModelScope.launch {
        cartItemRepository.insertCartItem(newCartItem)
    }
}

class ShopItemModelFactory(
    private val repository: ShopItemRepository,
    private val cartItemRepository: CartItemRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShopViewModel::class.java))
            return ShopViewModel(repository, cartItemRepository) as T
        throw IllegalArgumentException("Error")
    }
}