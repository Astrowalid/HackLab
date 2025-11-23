package com.example.hacklab.navigation

sealed class AppNavigation(val route: String) {
    object Card : AppNavigation("card")
    object GetStarted : AppNavigation("getstarted")
    object Profile : AppNavigation("profile")
    object SignUp : AppNavigation("signup")
    object Login : AppNavigation("login")
    object ProductList : AppNavigation("productlist")
    object ProductDetail : AppNavigation("productdetail")
}
