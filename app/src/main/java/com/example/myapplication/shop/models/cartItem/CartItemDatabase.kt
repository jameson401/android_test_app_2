package com.example.myapplication.cart.models.cartItem

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.shop.models.cartItem.CartItem

@Database(
    entities = [CartItem::class],
    version = 1,
    exportSchema = false
)
abstract class CartItemDatabase: RoomDatabase() {
    abstract fun cartItemDao(): CartItemDao

    companion object {
        @Volatile
        private var INSTANCE: CartItemDatabase? = null

        fun getDatabase(context: Context): CartItemDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CartItemDatabase::class.java,
                    "cart_item_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}