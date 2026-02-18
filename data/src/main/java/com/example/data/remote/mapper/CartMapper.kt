package com.example.data.remote.mapper

import com.example.data.remote.dto.CartDTO
import com.example.domain.model.Cart

fun CartDTO.toDomain(): Cart {
    return Cart(
        id = this.id,
        products = this.products.map { it.toDomain() }
    )
}
