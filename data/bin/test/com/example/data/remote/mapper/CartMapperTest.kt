package com.example.data.remote.mapper

import com.example.data.remote.dto.CartDTO
import com.example.data.remote.dto.ProductDTO
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe

class CartMapperTest : FunSpec({
    
    test("GIVEN CartDTO with products WHEN toDomain is called THEN should map Cart with all products") {
        // GIVEN
        val products = listOf(
            ProductDTO("prod-001", "Product 1", 10.0, "url1", 5),
            ProductDTO("prod-002", "Product 2", 20.0, "url2", 3)
        )
        val cartDTO = CartDTO(id = "cart-001", products = products)
        
        // WHEN
        val result = cartDTO.toDomain()
        
        // THEN
        result.id shouldBe cartDTO.id
        result.products shouldHaveSize 2
        result.products[0].id shouldBe "prod-001"
        result.products[1].id shouldBe "prod-002"
    }
    
    test("GIVEN CartDTO with empty products WHEN toDomain is called THEN should map Cart with empty list") {
        // GIVEN
        val cartDTO = CartDTO(id = "cart-empty", products = emptyList())
        
        // WHEN
        val result = cartDTO.toDomain()
        
        // THEN
        result.id shouldBe "cart-empty"
        result.products shouldHaveSize 0
    }
})
