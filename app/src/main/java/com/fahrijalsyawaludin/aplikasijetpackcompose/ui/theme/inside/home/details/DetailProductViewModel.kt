package com.fahrijalsyawaludin.aplikasijetpackcompose.ui.theme.inside.home.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fahrijalsyawaludin.aplikasijetpackcompose.data.ProductRepo
import com.fahrijalsyawaludin.aplikasijetpackcompose.models.OrderProduct
import com.fahrijalsyawaludin.aplikasijetpackcompose.models.Product
import com.fahrijalsyawaludin.aplikasijetpackcompose.ui.theme.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailProductViewModel(private val repository: ProductRepo) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<OrderProduct>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<OrderProduct>>
        get() = _uiState

    fun getProductById(productId: Long) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getOrderProductById(productId))
        }
    }

    fun addToStock(product: Product, count: Int) {
        viewModelScope.launch {
            repository.updateOrderProduct(product.id, count)
        }
    }
}