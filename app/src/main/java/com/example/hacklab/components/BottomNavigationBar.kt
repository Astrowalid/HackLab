package com.example.hacklab.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.hacklab.R
import com.example.hacklab.data.NavigationItem
import com.example.hacklab.navigation.AppNavigation
@Composable
fun BottomNavigationBar(
    navController: NavController
) {
    val selectedNavigationIndex = rememberSaveable {
        mutableIntStateOf(0)
    }
    val navigationItems = listOf(
        NavigationItem(
            title = stringResource(R.string.Marketplace_txt),
            icon = Icons.Default.Home,
            route = AppNavigation.ProductList.route
        ),
        NavigationItem(
            title = stringResource(R.string.Card_txt),
            icon = Icons.Default.ShoppingCart,
            route = AppNavigation.Card.route
        ),
        NavigationItem(
            title = stringResource(R.string.Profile_txt),
            icon = Icons.Default.Person,
            route = AppNavigation.Profile.route
        )
    )
    NavigationBar(
        containerColor = Color(0xFF131326),
        contentColor = Color.White
    ) {
        navigationItems.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedNavigationIndex.intValue == index,
                onClick = {
                    selectedNavigationIndex.intValue = index
                    navController.navigate(item.route)
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title,
                        tint = if (selectedNavigationIndex.intValue == index) {
                            Color(0xFFFFFFFF)
                        } else {
                            Color.Gray
                        }
                    )
                },
                label = {
                    Text(
                        item.title,
                        color = if (selectedNavigationIndex.intValue == index) {
                            Color.White
                        } else {
                            Color.Gray
                        }
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    unselectedIconColor = Color.Gray,
                    selectedTextColor = Color.White,
                    unselectedTextColor = Color.Gray,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun navigationbarscreen(){
    BottomNavigationBar(navController = rememberNavController())
}
