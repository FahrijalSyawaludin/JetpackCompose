package com.fahrijalsyawaludin.aplikasijetpackcompose.ui.theme.inside.home.checkout

import com.fahrijalsyawaludin.aplikasijetpackcompose.models.OrderProduct

data class CheckoutState(
    val orderProduct: List<OrderProduct>,
    val totalRequiredPoint: Int
)