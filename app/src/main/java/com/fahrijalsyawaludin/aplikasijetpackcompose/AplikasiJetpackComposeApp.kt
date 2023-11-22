package com.fahrijalsyawaludin.aplikasijetpackcompose

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.fahrijalsyawaludin.aplikasijetpackcompose.ui.theme.AplikasiJetpackComposeTheme
import com.fahrijalsyawaludin.aplikasijetpackcompose.ui.theme.inside.home.HomeScreen
import com.fahrijalsyawaludin.aplikasijetpackcompose.ui.theme.inside.home.profile.ProfileScreen
import com.fahrijalsyawaludin.aplikasijetpackcompose.ui.theme.inside.home.checkout.CheckoutScreen
import com.fahrijalsyawaludin.aplikasijetpackcompose.ui.theme.inside.home.details.DetailProductScreen
import com.fahrijalsyawaludin.aplikasijetpackcompose.ui.theme.nav.LayoutScreen
import com.fahrijalsyawaludin.aplikasijetpackcompose.ui.theme.nav.NavItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AplikasiJetpackComposeApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute != LayoutScreen.DetailProduct.route) {
                BottomBar(navController)
            }
        }, modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = LayoutScreen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(LayoutScreen.Home.route) {
                HomeScreen(navigateToDetail = { rewardId ->
                    navController.navigate(LayoutScreen.DetailProduct.createRoute(rewardId))
                })
            }
            composable(LayoutScreen.Checkout.route) {
                val context = LocalContext.current
                CheckoutScreen(onOrderButtonClicked = { message ->
                    shareOrder(context, message)
                })
            }
            composable(LayoutScreen.Profile.route) {
                ProfileScreen()
            }
            composable(
                route = LayoutScreen.DetailProduct.route,
                arguments = listOf(navArgument("productId") { type = NavType.LongType }),
            ) {
                val id = it.arguments?.getLong("productId") ?: -1L
                DetailProductScreen(productId = id, navigateBack = {
                    navController.navigateUp()
                }, navigateToCart = {
                    navController.popBackStack()
                    navController.navigate(LayoutScreen.Checkout.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                })
            }
        }
    }
}

private fun shareOrder(context: Context, summary: String) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.Jet_Product))
        putExtra(Intent.EXTRA_TEXT, summary)
    }

    context.startActivity(
        Intent.createChooser(
            intent, context.getString(R.string.Jet_Product)
        )
    )
}

@Composable
private fun BottomBar(
    navController: NavHostController, modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val navigationItems = listOf(
            NavItem(
                title = stringResource(R.string.menu_home),
                icon = Icons.Default.Home,
                layoutscreen = LayoutScreen.Home
            ),
            NavItem(
                title = stringResource(R.string.menu_stock),
                icon = Icons.Default.ShoppingCart,
                layoutscreen = LayoutScreen.Checkout
            ),
            NavItem(
                title = stringResource(R.string.menu_profile),
                icon = Icons.Default.AccountCircle,
                layoutscreen = LayoutScreen.Profile
            ),
        )
        navigationItems.map { item ->
            NavigationBarItem(icon = {
                Icon(
                    imageVector = item.icon, contentDescription = item.title
                )
            }, label = { Text(item.title) },
//                selected = false,
                selected = currentRoute == item.layoutscreen.route, onClick = {
                    navController.navigate(item.layoutscreen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun JetHeroesAppPreview() {
    AplikasiJetpackComposeTheme {
        AplikasiJetpackComposeApp()
    }
}