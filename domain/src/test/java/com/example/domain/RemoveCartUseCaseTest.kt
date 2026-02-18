package com.example.domain

import com.example.domain.repository.CartListRepository
import com.example.domain.usecase.RemoveCartUseCase
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify

class RemoveCartUseCaseTest : FunSpec({
    
    test("GIVEN valid cartId WHEN invoke is called THEN should call repository removeCart") {
        // GIVEN
        val mockRepository = mockk<CartListRepository>()
        val cartId = "cart-001"
        every { mockRepository.removeCart(cartId) } just runs
        val useCase = RemoveCartUseCase(mockRepository)
        
        // WHEN
        useCase(cartId)
        
        // THEN
        verify(exactly = 1) { mockRepository.removeCart(cartId) }
    }
    
    test("GIVEN repository throws exception WHEN invoke is called THEN should propagate exception") {
        // GIVEN
        val mockRepository = mockk<CartListRepository>()
        val cartId = "cart-001"
        val expectedException = RuntimeException("Delete failed")
        every { mockRepository.removeCart(cartId) } throws expectedException
        val useCase = RemoveCartUseCase(mockRepository)
        
        // WHEN
        val result = runCatching { useCase(cartId) }
        
        // THEN
        result.isFailure shouldBe true
        result.exceptionOrNull() shouldBe expectedException
    }
})
