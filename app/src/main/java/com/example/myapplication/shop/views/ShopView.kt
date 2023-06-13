package com.example.myapplication.shop.views

import android.annotation.SuppressLint
import android.os.CountDownTimer
import androidx.annotation.WorkerThread
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.BottomSheetState
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissState
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Icon
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.ThresholdConfig
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.rememberDismissState
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.assets.buttons.PressIconButton
import com.example.myapplication.shop.models.cartItem.CartItem
import com.example.myapplication.shop.models.shopItem.ShopItem
import com.example.myapplication.shop.viewModels.ShopViewModel
import com.example.myapplication.ui.theme.Padding
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import java.util.Timer

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ShopView(
    viewModel: ShopViewModel,
    modifier: Modifier = Modifier,
) {
    val shopItems: List<ShopItem> by viewModel.shopItems.collectAsStateWithLifecycle(initialValue = emptyList())

    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = false
    )
    val coroutineScope = rememberCoroutineScope()

    Box  {
        LazyColumn(
            modifier = modifier
        ) {
            items(items = shopItems, itemContent = { shopItem ->
                val currentShopItem by rememberUpdatedState(shopItem)
                val dismissState = rememberDismissState(
                    initialValue = DismissValue.Default
                )

                if (dismissState.currentValue == DismissValue.DismissedToStart) {
                    LaunchedEffect(Unit) {
                        dismissState.reset()
                        viewModel.deleteShopItem(currentShopItem)

                    }
                }

                SwipeToDismiss(
                    state = dismissState,
                    dismissContent = {
                        ShopItemCard(
                            shopItem = currentShopItem,
                            viewModel = viewModel
                        )
                    },
                    background = {},
                    directions = setOf(DismissDirection.EndToStart)
                )
            })
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(Padding.PaddingMedium.size)
                .background(
                    color = MaterialTheme.colorScheme.background.copy(alpha = 0f)
                ),
            contentAlignment = Alignment.BottomEnd,
        ) {
            IconButton(
                onClick = {
                    coroutineScope.launch {
                        if (modalSheetState.isVisible)
                            modalSheetState.hide()
                        else
                            modalSheetState.show()
                    }
                }
            ) {
                Icon(
                    Icons.Rounded.AddCircle,
                    tint =  MaterialTheme.colorScheme.secondary,
                    contentDescription = null,
                    modifier = Modifier.size(128.dp)
                )
            }
        }
    }
    AddShopItemBottomSheetView(
        viewModel,
        modalSheetState,
        modifier
    )
}

@Composable
private fun ShopItemCard(
    shopItem: ShopItem,
    viewModel: ShopViewModel
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
                    text = shopItem.name,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = shopItem.price,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
            PressIconButton(
                onClick = {
                    viewModel.addCartItem(CartItem(shopItem))
                },
                icon = {
                    Icon(Icons.Filled.ShoppingCart, contentDescription = null)
                },
                text = {
                    Text("Add to cart")
                }
            )
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun AddShopItemBottomSheetView(
    viewModel: ShopViewModel,
    modalSheetState: ModalBottomSheetState,
    modifier: Modifier = Modifier
) {
    ModalBottomSheetLayout(
        sheetState = modalSheetState,
        modifier = Modifier
            .zIndex(2f)
            .background(
                color = MaterialTheme.colorScheme.background.copy(alpha = 0f)
            ),
        sheetContent = {
            Box(modifier = modifier) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    var itemName by remember { mutableStateOf(TextFieldValue("")) }
                    var itemPrice by remember { mutableStateOf(TextFieldValue("")) }
                    TextField(
                        value = itemName,
                        onValueChange = {
                            itemName = it
                        },
                        label = { Text(text = "Item Name") },
                        placeholder = { Text(text = "Name of the item") },
                        modifier = Modifier.padding(Padding.PaddingSmall.size)
                    )
                    TextField(
                        value = itemPrice,
                        onValueChange = {
                            itemPrice = it
                        },
                        label = { Text(text = "Item Price") },
                        placeholder = { Text(text = "Price of the item") },
                        modifier = Modifier.padding(Padding.PaddingSmall.size),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    IconButton(
                        onClick = {
                            viewModel.addShopItem(
                                ShopItem(
                                    name = itemName.text,
                                    price = itemPrice.text
                                )
                            )

                            itemName = TextFieldValue("")
                            itemPrice = TextFieldValue("")
                        },
                        modifier = Modifier.background(
                            color = MaterialTheme.colorScheme.secondary,
                            shape = RoundedCornerShape(10.dp)
                        )
                    ) {
                        Icon(Icons.Default.Add, tint = Color.White, contentDescription = null)
                    }
                }
            }
        }
    ) {

    }
}
