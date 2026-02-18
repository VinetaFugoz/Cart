package com.example.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CartListDTO(
    @SerializedName(value = "cart_list")
    val carts: List<CartDTO>
)
