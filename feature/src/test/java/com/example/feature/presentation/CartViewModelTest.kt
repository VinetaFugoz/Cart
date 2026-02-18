package com.example.feature.presentation

import app.cash.turbine.test
import com.example.domain.model.Cart
import com.example.domain.usecase.GetCartListUseCase
import com.example.domain.usecase.RemoveCartUseCase
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain

@OptIn(ExperimentalCoroutinesApi::class)
class CartViewModelTest : FunSpec({

    val testDispatcher = StandardTestDispatcher()

    beforeTest {
        Dispatchers.setMain(testDispatcher)
    }

    afterTest {
        Dispatchers.resetMain()
    }

    test("GIVEN use case returns carts WHEN LoadCarts intent is sent THEN should emit Loading and Success") {
        runTest {
            // GIVEN
            val mockGetCartListUseCase = mockk<GetCartListUseCase>()
            val mockRemoveCartUseCase = mockk<RemoveCartUseCase>()
            val mockCarts = listOf(
                Cart(id = "cart-001", products = emptyList())
            )
            every { mockGetCartListUseCase() } returns mockCarts

            val viewModel = CartListViewModel(mockGetCartListUseCase, mockRemoveCartUseCase)

            // WHEN
            viewModel.uiState.test {
                viewModel.intent(CartListIntent.LoadCarts)
                testDispatcher.scheduler.advanceUntilIdle()

                // THEN
                (awaitItem().listState).shouldBeInstanceOf<CartListState.Loading>()
                val successState = awaitItem().listState
                successState.shouldBeInstanceOf<CartListState.Success>()
                (successState as CartListState.Success).cartList shouldBe mockCarts
            }
        }
    }

    test("GIVEN use case throws exception WHEN LoadCarts intent is sent THEN should emit Loading and Error") {
        runTest {
            // GIVEN
            val mockGetCartListUseCase = mockk<GetCartListUseCase>()
            val mockRemoveCartUseCase = mockk<RemoveCartUseCase>()
            val errorMessage = "Network error"
            every { mockGetCartListUseCase() } throws RuntimeException(errorMessage)

            val viewModel = CartListViewModel(mockGetCartListUseCase, mockRemoveCartUseCase)

            // WHEN
            viewModel.uiState.test {
                viewModel.intent(CartListIntent.LoadCarts)
                testDispatcher.scheduler.advanceUntilIdle()

                // THEN
                (awaitItem().listState).shouldBeInstanceOf<CartListState.Loading>()
                val errorState = awaitItem().listState
                errorState.shouldBeInstanceOf<CartListState.Error>()
                (errorState as CartListState.Error).error.message shouldBe errorMessage
            }
        }
    }

    test("GIVEN valid cartId WHEN ConfirmDeleteCart intent is sent THEN should remove cart and reload list") {
        runTest {
            // GIVEN
            val mockGetCartListUseCase = mockk<GetCartListUseCase>()
            val mockRemoveCartUseCase = mockk<RemoveCartUseCase>()
            val cartId = "cart-001"
            val initialCarts = listOf(
                Cart(id = cartId, products = emptyList()),
                Cart(id = "cart-002", products = emptyList())
            )
            val updatedCarts = listOf(
                Cart(id = "cart-002", products = emptyList())
            )

            every { mockGetCartListUseCase() } returnsMany listOf(initialCarts, updatedCarts)
            every { mockRemoveCartUseCase(cartId) } just runs

            val viewModel = CartListViewModel(mockGetCartListUseCase, mockRemoveCartUseCase)

            // WHEN
            viewModel.intent(CartListIntent.LoadCarts)
            testDispatcher.scheduler.advanceUntilIdle()

            viewModel.intent(CartListIntent.ConfirmDeleteCart(cartId))
            testDispatcher.scheduler.advanceUntilIdle()

            // THEN
            coVerify(exactly = 1) { mockRemoveCartUseCase(cartId) }
            coVerify(exactly = 2) { mockGetCartListUseCase() }
        }
    }

    test("GIVEN remove cart fails WHEN ConfirmDeleteCart intent is sent THEN should emit Error state") {
        runTest {
            // GIVEN
            val mockGetCartListUseCase = mockk<GetCartListUseCase>()
            val mockRemoveCartUseCase = mockk<RemoveCartUseCase>()
            val cartId = "cart-001"
            val errorMessage = "Delete failed"
            val initialCarts = listOf(Cart(id = cartId, products = emptyList()))

            every { mockGetCartListUseCase() } returns initialCarts
            every { mockRemoveCartUseCase(cartId) } throws RuntimeException(errorMessage)

            val viewModel = CartListViewModel(mockGetCartListUseCase, mockRemoveCartUseCase)

            // WHEN
            viewModel.uiState.test {
                viewModel.intent(CartListIntent.LoadCarts)
                testDispatcher.scheduler.advanceUntilIdle()

                awaitItem() // Loading
                awaitItem() // Success with initial carts

                viewModel.intent(CartListIntent.ConfirmDeleteCart(cartId))
                testDispatcher.scheduler.advanceUntilIdle()

                // THEN
                val errorState = awaitItem().listState
                errorState.shouldBeInstanceOf<CartListState.Error>()
                (errorState as CartListState.Error).error.message shouldBe errorMessage
            }
        }
    }

    test("GIVEN use case returns empty list WHEN LoadCarts intent is sent THEN should emit EmptyResult") {
        runTest {
            // GIVEN
            val mockGetCartListUseCase = mockk<GetCartListUseCase>()
            val mockRemoveCartUseCase = mockk<RemoveCartUseCase>()
            every { mockGetCartListUseCase() } returns emptyList()

            val viewModel = CartListViewModel(mockGetCartListUseCase, mockRemoveCartUseCase)

            // WHEN
            viewModel.uiState.test {
                viewModel.intent(CartListIntent.LoadCarts)
                testDispatcher.scheduler.advanceUntilIdle()

                // THEN
                (awaitItem().listState).shouldBeInstanceOf<CartListState.Loading>()
                val emptyState = awaitItem().listState
                emptyState.shouldBeInstanceOf<CartListState.EmptyResult>()
            }
        }
    }
})
