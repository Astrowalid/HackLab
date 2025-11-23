package com.example.hacklab.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.hacklab.components.BottomNavigationBar
import com.example.hacklab.navigation.AppNavigation


@Composable
fun MainScreen() {
    val navController = rememberNavController()

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    val screensWithoutBars = listOf(
        AppNavigation.Login.route,
        AppNavigation.SignUp.route,
        AppNavigation.GetStarted.route
    )

    val showBars = currentRoute !in screensWithoutBars

    val currentScreenTitle = getTitleForRoute(currentRoute)

    Scaffold(
        topBar = {
            if (showBars) {
                when {
                    currentRoute == AppNavigation.Profile.route ||
                            currentRoute == AppNavigation.Card.route -> {
                        TopNavigationBarType2(
                            navController = navController, title = currentScreenTitle
                        )
                    }

                    else -> {
                        TopNavigationBar(navController = navController,)
                    }
                }
            }
        },
        bottomBar = {
            if (showBars && shouldShowBottomBar(currentRoute)) {
                BottomNavigationBar(navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = AppNavigation.Splash.route,
            modifier = Modifier.padding(innerPadding)
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
}

    private fun shouldShowBottomBar(route: String?): Boolean {
        val screensWithBottomBar = listOf(
            AppNavigation.ProductList.route,
            AppNavigation.Card.route,
            AppNavigation.Profile.route,
            AppNavigation.ProductDetail.route
        )
        return route in screensWithBottomBar
    }

    private fun getTitleForRoute(route: String?): String {
        return when (route?.substringBefore('/')) {
            AppNavigation.ProductList.route -> "Marketplace"
            AppNavigation.Card.route -> "My Cart"
            AppNavigation.Profile.route -> "My Profile"
            AppNavigation.ProductDetail.route -> "Product Details"
            AppNavigation.Login.route -> "Login"
            AppNavigation.SignUp.route -> "Sign Up"
            AppNavigation.GetStarted.route -> "Get Started"
            else -> "HackLab"
        }
    }