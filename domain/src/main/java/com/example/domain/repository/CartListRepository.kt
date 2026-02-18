package com.example.domain.repository

import com.example.domain.model.Cart

interface CartListRepository {
    fun getCartList(): List<Cart>
    fun removeCart(cartId: String)
}
