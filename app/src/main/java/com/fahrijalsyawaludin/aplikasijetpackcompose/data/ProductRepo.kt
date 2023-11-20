package com.fahrijalsyawaludin.aplikasijetpackcompose.data

import com.fahrijalsyawaludin.aplikasijetpackcompose.models.FakeProductData
import com.fahrijalsyawaludin.aplikasijetpackcompose.models.OrderProduct
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class ProductRepo {
    private val orderProduct = mutableListOf<OrderProduct>()

    init {
        if (orderProduct.isEmpty()) {
            FakeProductData.dummyProduct.forEach {
                orderProduct.add(OrderProduct(it, 0))
            }
        }
    }

    fun getAllProduct(): Flow<List<OrderProduct>> {
        return flowOf(orderProduct)
    }

    fun getOrderProductById(rewardId: Long): OrderProduct {
        return orderProduct.first {
            it.product.id == rewardId
        }
    }

    fun updateOrderProduct(productId: Long, newCountValue: Int): Flow<Boolean> {
        val index = orderProduct.indexOfFirst { it.product.id == productId }
        val result = if (index >= 0) {
            val OrderProduct = orderProduct[index]
            orderProduct[index] =
                OrderProduct.copy(product = OrderProduct.product, count = newCountValue)
            true
        } else {
            false
        }
        return flowOf(result)
    }

    fun getAddedOrderProduct(): Flow<List<OrderProduct>> {
        return getAllProduct()
            .map { orderProduct ->
                orderProduct.filter { OrderProduct ->
                    OrderProduct.count != 0
                }
            }
    }

    companion object {
        @Volatile
        private var instance: ProductRepo? = null

        fun getInstance(): ProductRepo =
            instance ?: synchronized(this) {
                ProductRepo().apply {
                    instance = this
                }
            }
    }
}
