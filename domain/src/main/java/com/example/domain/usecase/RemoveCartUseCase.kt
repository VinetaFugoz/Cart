package com.example.domain.usecase

import com.example.domain.repository.CartListRepository

class RemoveCartUseCase(
    private val cartListRepository: CartListRepository
) {
    operator fun invoke(cartId: String) {
        cartListRepository.removeCart(cartId)
    }
}
