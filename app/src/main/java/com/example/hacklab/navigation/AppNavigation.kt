package com.example.hacklab.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.hacklab.screens.ProductListScreen
import com.example.hacklab.screens.ProductDetailScreen
import com.example.hacklab.screens.LoginScreen


sealed class AppNavigation(val route: String) {
    object Card : AppNavigation("card")
    object GetStarted : AppNavigation("getstarted")
    object Profile : AppNavigation("profile")
    object SignUp : AppNavigation("signup")
    object Login : AppNavigation("login")
    object ProductList : AppNavigation("productlist")
    object ProductDetail : AppNavigation("productdetail/{productId}") {
        fun createRoute(productId: Int) = "productdetail/$productId"
    }
}


@Composable
fun AppNavigationHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppNavigation.Login.route
    ) {
        composable(AppNavigation.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(AppNavigation.ProductList.route) {
                        popUpTo(AppNavigation.Login.route) { inclusive = true }
                    }
                },
                onSignUpClick = {
                    // TODO: Implémentation de SignUp plus tard
                    println("Sign Up clicked")
                }
            )
        }


        composable(AppNavigation.ProductList.route) {
            ProductListScreen(
                onProductClick = { productId ->
                    navController.navigate(
                        AppNavigation.ProductDetail.createRoute(productId)
                    )
                }
            )
        }


        composable(
            route = AppNavigation.ProductDetail.route,
            arguments = listOf(
                navArgument("productId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId")

            ProductDetailScreen(
                productId = productId,
                onBackClick = {
                    navController.popBackStack()
                },
                onAddToCart = {
                    println("✅ Produit $productId ajouté au panier")
                }
            )
        }
    }
}