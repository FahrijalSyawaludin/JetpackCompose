package com.fahrijalsyawaludin.aplikasijetpackcompose.ui.theme.nav

sealed class LayoutScreen(val route: String) {
    object Home : LayoutScreen("home")
    object Checkout : LayoutScreen("checkout")
    object Profile : LayoutScreen("profile")
    object DetailProduct : LayoutScreen("home/{productId}") {
        fun createRoute(productId: Long) = "home/$productId"
    }
}