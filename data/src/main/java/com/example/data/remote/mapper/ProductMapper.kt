package com.example.data.remote.mapper

import com.example.data.remote.dto.ProductDTO
import com.example.domain.model.Product

fun ProductDTO.toDomain(): Product {
    return Product(
        id = this.id,
        name = this.name,
        price = this.price,
        imageUrl = this.imageUrl,
        quantity = this.quantity
    )
}
