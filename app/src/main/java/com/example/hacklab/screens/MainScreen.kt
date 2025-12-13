package com.example.hacklab.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.hacklab.components.BottomNavigationBar
import com.example.hacklab.components.TopNavigationBar
import com.example.hacklab.navigation.AppNavigation
import com.example.hacklab.utils.SessionManager
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.collectLatest

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

    // Observer le timeout de session
    LaunchedEffect(Unit) {
        SessionManager.timeoutFlow.collectLatest {
            // Déconnexion Firebase
            FirebaseAuth.getInstance().signOut()
            
            // Redirection vers Login
            // On vérifie qu'on n'est pas déjà sur l'écran de login ou signup pour éviter les boucles
            if (currentRoute != AppNavigation.Login.route && currentRoute != AppNavigation.SignUp.route) {
                navController.navigate(AppNavigation.Login.route) {
                    popUpTo(0) { inclusive = true } // Vider la stack de navigation
                    launchSingleTop = true
                }
            }
        }
    }
    
    // Si on navigue manuellement vers Login, on peut arrêter le timer si on le souhaite
    LaunchedEffect(currentRoute) {
        if (currentRoute == AppNavigation.Login.route) {
            SessionManager.stopTimer()
        }
    }

    Scaffold(
        topBar = {
            if (showBars) {
                when {
                    currentRoute == AppNavigation.Profile.route ||
                            currentRoute == AppNavigation.Card.route -> {
                        TopNavigationBar(navController = navController,currentScreenTitle)
                    }
                    else -> {

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
                        // Démarrer la session après connexion réussie
                        SessionManager.startUserSession()
                        navController.navigate(AppNavigation.ProductList.route) {
                            popUpTo(AppNavigation.Login.route) { inclusive = true }
                        }
                    },
                    onSignUpClick = {
                        navController.navigate(AppNavigation.SignUp.route)
                    }
                )
            }

            composable(AppNavigation.SignUp.route) {
                SignupScreen(
                    onSignUpSuccess = {
                         // Démarrer la session après inscription réussie
                        SessionManager.startUserSession()
                        navController.navigate(AppNavigation.GetStarted.route) {
                            popUpTo(AppNavigation.SignUp.route) { inclusive = true }
                        }
                    },
                    onLoginClick = {
                        navController.popBackStack()
                    }
                )
            }

            composable(AppNavigation.GetStarted.route) {
                GetStarted(
                    onGetStartedClick = {
                        navController.navigate(AppNavigation.ProductList.route) {
                            popUpTo(AppNavigation.GetStarted.route) { inclusive = true }
                        }
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
                    onBackClick = { navController.popBackStack() },
                    onCartClick = {
                        navController.navigate(AppNavigation.Card.route)
                    },
                    navController = navController
                )
            }
            composable(AppNavigation.Card.route) {
                CartScreen(
                    navController = navController
                )
            }

            composable(AppNavigation.Profile.route) {
                Profile(
                    navController = navController
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
        AppNavigation.Card.route -> "Cart"
        AppNavigation.Profile.route -> "Profile"
        AppNavigation.ProductDetail.route -> "Product Details"
        AppNavigation.Login.route -> "Login"
        AppNavigation.SignUp.route -> "Sign Up"
        AppNavigation.GetStarted.route -> "Get Started"
        else -> "HackLab"
    }
}