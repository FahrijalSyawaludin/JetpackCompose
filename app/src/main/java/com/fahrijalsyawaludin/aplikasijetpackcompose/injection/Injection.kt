package com.fahrijalsyawaludin.aplikasijetpackcompose.injection

import com.fahrijalsyawaludin.aplikasijetpackcompose.data.ProductRepo

object Injection {
    fun provideRepository(): ProductRepo {
        return ProductRepo.getInstance()
    }
}