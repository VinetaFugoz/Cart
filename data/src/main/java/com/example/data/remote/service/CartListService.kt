package com.example.data.remote.service

import com.example.data.remote.dto.CartListDTO
import com.google.gson.Gson

class CartListService {
    fun getCartList(): CartListDTO {
        val json = this::class.java.classLoader
            ?.getResourceAsStream("cart_list.json")
            ?.bufferedReader()
            ?.use { it.readText() }
            ?: throw IllegalStateException("cart_list.json not found")
        return Gson().fromJson(json, CartListDTO::class.java)
    }
}
