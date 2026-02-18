package com.example.feature.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.domain.model.Cart
import com.example.feature.R
import com.example.feature.presentation.components.CartCard
import com.example.feature.presentation.preview.CartSampleData
import org.koin.androidx.compose.koinViewModel

@Composable
fun CartListScreen(
    viewModel: CartListViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.intent(CartListIntent.LoadCarts)
    }

    Scaffold(
        topBar = {
            CartTopBar()
        },
        content = { paddingValues ->
            CartContent(
                paddingValues = paddingValues,
                cartListState = uiState.listState,
                onIntent = { viewModel.intent(it) }
            )
        }
    )

    CartOverlay(uiState.overlayState, viewModel)
}

@Composable
private fun CartOverlay(uiState: CartOverlayState, viewModel: CartListViewModel) {
    when (uiState) {
        is CartOverlayState.DeleteCartDialog -> DeleteCardDialog(
            cartId = uiState.cartId,
            productCount = uiState.productCount,
            onDismiss = { viewModel.intent(CartListIntent.CancelDeleteCart) },
            onIntent = { viewModel.intent(it) }
        )

        CartOverlayState.None -> {}
    }
}

@Composable
fun DeleteCardDialog(
    cartId: String,
    productCount: Int,
    onDismiss: () -> Unit,
    onIntent: (CartListIntent) -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.cart_dialog_confirm_delete_title)) },
        text = {
            Text(
                stringResource(
                    R.string.cart_dialog_confirm_delete_message,
                    productCount
                )
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onIntent.invoke(CartListIntent.ConfirmDeleteCart(cartId))
                }
            ) {
                Text(
                    stringResource(R.string.cart_dialog_confirm),
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onIntent.invoke(CartListIntent.CancelDeleteCart)
                }
            ) {
                Text(stringResource(R.string.cart_dialog_cancel))
            }
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CartTopBar() = TopAppBar(title = { Text(stringResource(R.string.cart_screen_title)) })

@Composable
private fun CartContent(
    paddingValues: PaddingValues,
    cartListState: CartListState,
    onIntent: (CartListIntent) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        when (cartListState) {
            CartListState.Loading -> OnLoading()
            CartListState.EmptyResult -> OnEmptyResult()
            is CartListState.Error -> OnError(message = cartListState.error.message.toString())
            is CartListState.Success -> OnSuccess(
                cartList = cartListState.cartList,
                onIntent = onIntent
            )
        }
    }
}

@Composable
private fun OnLoading() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun OnEmptyResult() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.cart_empty_message),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun OnError(message: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.cart_error_message, message),
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
private fun OnSuccess(
    cartList: List<Cart>,
    onIntent: (CartListIntent) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(dimensionResource(R.dimen.spacing_large)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_small))
    ) {
        items(cartList) { cart ->
            CartCard(
                cart = cart,
                onDeleteClick = { cartId -> onIntent(CartListIntent.RequestDeleteCart(cartId, cartList)) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CartTopBarPreview() {
    MaterialTheme {
        CartTopBar()
    }
}

@Preview(showBackground = true, heightDp = 400)
@Composable
private fun CartContentLoadingPreview() {
    MaterialTheme {
        CartContent(
            paddingValues = PaddingValues(0.dp),
            cartListState = CartListState.Loading,
            onIntent = {}
        )
    }
}

@Preview(showBackground = true, heightDp = 400)
@Composable
private fun CartContentEmptyPreview() {
    MaterialTheme {
        CartContent(
            paddingValues = PaddingValues(0.dp),
            cartListState = CartListState.EmptyResult,
            onIntent = {}
        )
    }
}

@Preview(showBackground = true, heightDp = 400)
@Composable
private fun CartContentErrorPreview() {
    MaterialTheme {
        CartContent(
            paddingValues = PaddingValues(0.dp),
            cartListState = CartListState.Error(Throwable("Failed to load carts")),
            onIntent = {}
        )
    }
}

@Preview(showBackground = true, heightDp = 600)
@Composable
private fun CartContentSuccessPreview() {
    MaterialTheme {
        CartContent(
            paddingValues = PaddingValues(0.dp),
            cartListState = CartListState.Success(CartSampleData.carts),
            onIntent = {}
        )
    }
}
