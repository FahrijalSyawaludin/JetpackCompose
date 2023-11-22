package com.fahrijalsyawaludin.aplikasijetpackcompose.models

data class Product(
    val id: Long,
    val image: Int,
    val title: String,
    val description: String,
    val requiredPoint: Int,
)