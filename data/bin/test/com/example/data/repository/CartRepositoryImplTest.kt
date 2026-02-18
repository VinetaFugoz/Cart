package com.example.data.repository

import com.example.data.remote.dto.CartDTO
import com.example.data.remote.dto.CartListDTO
import com.example.data.remote.dto.ProductDTO
import com.example.data.remote.service.CartListService
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class CartRepositoryImplTest : FunSpec({

    test("GIVEN service returns carts WHEN getCartList is called THEN should return mapped domain carts") {
        // GIVEN
        val mockService = mockk<CartListService>()
        val productsDTO = listOf(
            ProductDTO("prod-001", "Product 1", 10.0, "url", 5)
        )
        val cartsDTO = listOf(
            CartDTO("cart-001", productsDTO)
        )
        val response = CartListDTO(cartsDTO)
        every { mockService.getCartList() } returns response

        val repository = CartListRepositoryImpl(mockService)

        // WHEN
        val result = repository.getCartList()

        // THEN
        result shouldHaveSize 1
        result[0].id shouldBe "cart-001"
        result[0].products shouldHaveSize 1
        result[0].products[0].name shouldBe "Product 1"
    }

    test("GIVEN service returns empty carts WHEN getCartList is called THEN should return empty list") {
        // GIVEN
        val mockService = mockk<CartListService>()
        val response = CartListDTO(emptyList())
        every { mockService.getCartList() } returns response

        val repository = CartListRepositoryImpl(mockService)

        // WHEN
        val result = repository.getCartList()

        // THEN
        result shouldHaveSize 0
    }
})
