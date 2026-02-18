package com.example.feature.presentation

import com.example.domain.model.Cart

sealed class CartListState {
    data object Loading : CartListState()
    data object EmptyResult : CartListState()
    data class Error(val error: Throwable) : CartListState()
    data class Success(val cartList: List<Cart>) : CartListState()
}

sealed class CartOverlayState {
    data object None : CartOverlayState()
    data class DeleteCartDialog(val cartId: String, val productCount: Int) : CartOverlayState()
}

data class CartUiState(
    val listState: CartListState = CartListState.Loading,
    val overlayState: CartOverlayState = CartOverlayState.None
)