package com.example.domain.usecase

import com.example.domain.model.Cart
import com.example.domain.repository.CartListRepository

class GetCartListUseCase(
    private val cartListRepository: CartListRepository
) {
    operator fun invoke(): List<Cart> {
        return cartListRepository.getCartList()
    }
}
