package com.example.myapplication.shop.models.cartItem

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.example.myapplication.shop.models.shopItem.ShopItem

@Entity(tableName = "cart_item_table")
data class CartItem(
    @Embedded val item: ShopItem,
    @PrimaryKey(autoGenerate = true) var cartItemId: Int = 0
)
