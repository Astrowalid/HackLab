package com.example.hacklab.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.hacklab.navigation.AppNavigation


@Composable
fun AppNavigationHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppNavigation.Splash.route
    ) {
        composable(AppNavigation.Splash.route) {
            SplashScreen(
                onSplashFinished = {
                    navController.navigate(AppNavigation.Login.route) {
                        popUpTo(AppNavigation.Splash.route) { inclusive = true }
                    }
                }
            )
        }

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