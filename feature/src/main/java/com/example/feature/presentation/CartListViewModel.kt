package com.example.feature.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Cart
import com.example.domain.usecase.GetCartListUseCase
import com.example.domain.usecase.RemoveCartUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CartListViewModel(
    private val getCartListUseCase: GetCartListUseCase,
    private val removeCartUseCase: RemoveCartUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CartUiState())
    val uiState: StateFlow<CartUiState> = _uiState.asStateFlow()

    fun intent(intent: CartListIntent) {
        when (intent) {
            CartListIntent.LoadCarts -> loadCartList()
            is CartListIntent.RequestDeleteCart -> requestDeleteCart(intent.cartId, intent.cartList)
            CartListIntent.CancelDeleteCart -> clearDeleteDialog()
            is CartListIntent.ConfirmDeleteCart -> deleteCart(intent.cartId)
        }
    }

    private fun loadCartList() {
        viewModelScope.launch {
            _uiState.update { it.copy(listState = CartListState.Loading) }
            delay(2000)
            try {
                val cartList = getCartListUseCase()
                _uiState.update {
                    it.copy(
                        listState = if (cartList.isEmpty()) {
                            CartListState.EmptyResult
                        } else {
                            CartListState.Success(cartList)
                        }
                    )
                }
            } catch (exception: Exception) {
                _uiState.update { it.copy(listState = CartListState.Error(exception)) }
            }
        }
    }


    private fun requestDeleteCart(cartId: String, cartList: List<Cart>) {
        val productCount = cartList.find { it.id == cartId }?.products?.size ?: 0
        _uiState.update {
            it.copy(
                overlayState = CartOverlayState.DeleteCartDialog(
                    cartId = cartId,
                    productCount = productCount
                )
            )
        }
    }

    private fun clearDeleteDialog() {
        _uiState.update { it.copy(overlayState = CartOverlayState.None) }
    }

    private fun deleteCart(cartId: String) {
        viewModelScope.launch {
            clearDeleteDialog()
            try {
                removeCartUseCase(cartId)
                loadCartList()
            } catch (exception: Exception) {
                _uiState.update { it.copy(listState = CartListState.Error(exception)) }
            }
        }
    }
}
