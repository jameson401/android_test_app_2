package com.example.myapplication.shop.models.shopItem

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shop_item_table")
data class ShopItem(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "price") val price: String,
    @PrimaryKey(autoGenerate = true) var id: Int = 0
)
