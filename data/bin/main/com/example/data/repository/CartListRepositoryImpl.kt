package com.example.data.repository

import com.example.data.remote.mapper.toDomain
import com.example.data.remote.service.CartListService
import com.example.domain.model.Cart
import com.example.domain.repository.CartListRepository

class CartListRepositoryImpl(private val cartListService: CartListService = CartListService()) : CartListRepository {

    private val cache = mutableListOf<Cart>()

    override fun getCartList(): List<Cart> {
        if (cache.isEmpty()) {
            val response = cartListService.getCartList()
            cache.addAll(response.carts.map { it.toDomain() })
        }
        return cache.toList()
    }

    override fun removeCart(cartId: String) {
        cache.removeAll { it.id == cartId }
    }
}
