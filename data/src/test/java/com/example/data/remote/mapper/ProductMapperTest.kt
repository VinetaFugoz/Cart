package com.example.data.remote.mapper

import com.example.data.remote.dto.ProductDTO
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class ProductMapperTest : FunSpec({
    
    test("GIVEN valid ProductDTO WHEN toDomain is called THEN should map to Product correctly") {
        // GIVEN
        val productDTO = ProductDTO(
            id = "prod-001",
            name = "Test Product",
            price = 9.99,
            imageUrl = "https://example.com/image.jpg",
            quantity = 10
        )
        
        // WHEN
        val result = productDTO.toDomain()
        
        // THEN
        result.id shouldBe productDTO.id
        result.name shouldBe productDTO.name
        result.price shouldBe productDTO.price
        result.imageUrl shouldBe productDTO.imageUrl
        result.quantity shouldBe productDTO.quantity
    }
    
    test("GIVEN ProductDTO with zero quantity WHEN toDomain is called THEN should preserve zero values") {
        // GIVEN
        val productDTO = ProductDTO(
            id = "prod-002",
            name = "Empty Product",
            price = 0.0,
            imageUrl = "",
            quantity = 0
        )
        
        // WHEN
        val result = productDTO.toDomain()
        
        // THEN
        result.quantity shouldBe 0
        result.price shouldBe 0.0
    }
})
