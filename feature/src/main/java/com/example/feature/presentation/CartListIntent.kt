package com.example.feature.presentation

import com.example.domain.model.Cart

sealed class CartListIntent {
    data object LoadCarts : CartListIntent()
    data class RequestDeleteCart(val cartId: String, val cartList: List<Cart>) : CartListIntent()
    data object CancelDeleteCart : CartListIntent()
    data class ConfirmDeleteCart(val cartId: String) : CartListIntent()
}