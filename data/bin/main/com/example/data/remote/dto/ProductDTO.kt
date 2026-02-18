package com.example.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ProductDTO(
    @SerializedName(value = "id")
    val id: String,
    @SerializedName(value = "name")
    val name: String,
    @SerializedName(value = "price")
    val price: Double,
    @SerializedName(value = "imageUrl")
    val imageUrl: String,
    @SerializedName(value = "quantity")
    val quantity: Int
)
