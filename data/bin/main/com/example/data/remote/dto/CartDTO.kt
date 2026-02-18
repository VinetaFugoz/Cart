package com.example.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CartDTO(
    @SerializedName(value = "id")
    val id: String,
    @SerializedName(value = "products")
    val products: List<ProductDTO>
)
