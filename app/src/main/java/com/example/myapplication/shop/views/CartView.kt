package com.example.myapplication.shop.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DismissValue
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.myapplication.assets.buttons.PressIconButton
import com.example.myapplication.cart.viewModels.CartViewModel
import com.example.myapplication.shop.models.cartItem.CartItem
import com.example.myapplication.shop.models.shopItem.ShopItem
import com.example.myapplication.ui.theme.Padding

@Composable
fun CartView(
    viewModel: CartViewModel,
    modifier: Modifier = Modifier,

) {
    val cartItems: List<CartItem> by viewModel.cartItems.collectAsStateWithLifecycle(initialValue = emptyList())

    LazyColumn(
        modifier = modifier
    ) {
        items(items = cartItems, itemContent = { cartItem ->
            CartItemCard(
                viewModel,
                cartItem
            )
        })
    }
}

@Composable
private fun CartItemCard(
    viewModel: CartViewModel,
    cartItem: CartItem
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        ),
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier.padding(Padding.PaddingMedium.size)
    ) {
        Row(modifier = Modifier.padding(Padding.PaddingExtraLarge.size)) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = cartItem.item.name,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = cartItem.item.price,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
            PressIconButton(
                onClick = {
                    viewModel.deleteCartItem(cartItem)
                },
                icon = {
                    Icon(Icons.Filled.Clear, contentDescription = null)
                },
                text = {
                    Text("Remove from cart")
                }
            )
        }
    }
}