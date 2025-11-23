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
import com.example.hacklab.screens.SplashScreen


sealed class AppNavigation(val route: String) {
    object Splash : AppNavigation("splash")
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

