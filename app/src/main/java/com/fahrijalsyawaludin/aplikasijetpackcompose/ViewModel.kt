package com.fahrijalsyawaludin.aplikasijetpackcompose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fahrijalsyawaludin.aplikasijetpackcompose.data.ProductRepo
import com.fahrijalsyawaludin.aplikasijetpackcompose.ui.theme.inside.home.HomeViewModel
import com.fahrijalsyawaludin.aplikasijetpackcompose.ui.theme.inside.home.checkout.CheckoutViewModel
import com.fahrijalsyawaludin.aplikasijetpackcompose.ui.theme.inside.home.details.DetailProductViewModel

class ViewModel(private val repository: ProductRepo) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DetailProductViewModel::class.java)) {
            return DetailProductViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(CheckoutViewModel::class.java)) {
            return CheckoutViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}