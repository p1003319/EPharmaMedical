package com.example.epharma

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationItem(val route: String, val label: String, val icons: ImageVector) {

    object Home : NavigationItem("home", "Home", Icons.Default.Home)
    object Notifications :
        NavigationItem("Cart", "Cart", Icons.Default.ShoppingCart)

    object Settings : NavigationItem("Locate","Locate",Icons.Default.LocationOn)

    object Account: NavigationItem("account","Account",Icons.Default.AccountCircle)

}