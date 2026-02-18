package com.example.domain

import com.example.domain.model.Cart
import com.example.domain.repository.CartListRepository
import com.example.domain.usecase.GetCartListUseCase
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class GetCartUseCaseTest : FunSpec({
    
    test("GIVEN repository returns carts WHEN invoke is called THEN should return list of carts") {
        // GIVEN
        val mockRepository = mockk<CartListRepository>()
        val expectedCarts = listOf(
            Cart(id = "cart-001", products = emptyList()),
            Cart(id = "cart-002", products = emptyList())
        )
        every { mockRepository.getCart() } returns expectedCarts
        val useCase = GetCartListUseCase(mockRepository)
        
        // WHEN
        val result = useCase()
        
        // THEN
        result shouldBe expectedCarts
        verify(exactly = 1) { mockRepository.getCart() }
    }
    
    test("GIVEN repository returns empty list WHEN invoke is called THEN should return empty list") {
        // GIVEN
        val mockRepository = mockk<CartListRepository>()
        every { mockRepository.getCart() } returns emptyList()
        val useCase = GetCartListUseCase(mockRepository)
        
        // WHEN
        val result = useCase()
        
        // THEN
        result shouldBe emptyList()
        verify(exactly = 1) { mockRepository.getCart() }
    }
    
    test("GIVEN repository throws exception WHEN invoke is called THEN should propagate exception") {
        // GIVEN
        val mockRepository = mockk<CartListRepository>()
        val expectedException = RuntimeException("Network error")
        every { mockRepository.getCart() } throws expectedException
        val useCase = GetCartListUseCase(mockRepository)
        
        // WHEN
        val result = runCatching { useCase() }
        
        // THEN
        result.isFailure shouldBe true
        result.exceptionOrNull() shouldBe expectedException
    }
})
