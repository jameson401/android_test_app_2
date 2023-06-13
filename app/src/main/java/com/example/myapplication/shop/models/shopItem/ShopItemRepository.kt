package com.example.myapplication.shop.models.shopItem

import androidx.annotation.WorkerThread
import com.example.myapplication.shop.models.shopItem.ShopItem
import com.example.myapplication.shop.models.shopItem.ShopItemDao
import kotlinx.coroutines.flow.Flow

class ShopItemRepository(
    private val shopItemDao: ShopItemDao
) {
    val allShopItems: Flow<List<ShopItem>> = shopItemDao.allShopItems()

    @WorkerThread
    suspend fun insertShopItem(shopItem: ShopItem) {
        shopItemDao.insertShopItem(shopItem)
    }

    @WorkerThread
    suspend fun updateShopItem(shopItem: ShopItem) {
        shopItemDao.updateShopItem(shopItem)
    }

    @WorkerThread
    suspend fun deleteShopItem(shopItem: ShopItem) {
        shopItemDao.deleteShopItem(shopItem)
    }
}