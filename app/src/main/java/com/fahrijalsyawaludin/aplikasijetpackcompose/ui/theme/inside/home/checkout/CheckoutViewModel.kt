package com.fahrijalsyawaludin.aplikasijetpackcompose.ui.theme.inside.home.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fahrijalsyawaludin.aplikasijetpackcompose.data.ProductRepo
import com.fahrijalsyawaludin.aplikasijetpackcompose.ui.theme.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CheckoutViewModel(private val repository: ProductRepo) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<CheckoutState>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<CheckoutState>>
        get() = _uiState

    fun getAddedOrderProduct() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.getAddedOrderProduct()
                .collect { orderProduct ->
                    val totalRequiredPoint =
                        orderProduct.sumOf { it.product.requiredPoint * it.count }
                    _uiState.value =
                        UiState.Success(CheckoutState(orderProduct, totalRequiredPoint))
                }
        }
    }

    fun updateOrderProduct(rewardId: Long, count: Int) {
        viewModelScope.launch {
            repository.updateOrderProduct(rewardId, count)
                .collect { isUpdated ->
                    if (isUpdated) {
                        getAddedOrderProduct()
                    }
                }
        }
    }
}
