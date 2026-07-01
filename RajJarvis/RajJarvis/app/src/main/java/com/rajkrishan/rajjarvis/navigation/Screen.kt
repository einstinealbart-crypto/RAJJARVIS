package com.rajkrishan.rajjarvis.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val label: String, val icon: ImageVector) {
    data object Splash : Screen("splash", "Splash", Icons.Filled.Home)
    data object Home : Screen("home", "Home", Icons.Filled.Home)
    data object Assistant : Screen("assistant", "Assistant", Icons.Filled.SmartToy)
    data object Search : Screen("search", "Search", Icons.Filled.Search)
    data object Files : Screen("files", "Files", Icons.Filled.Folder)
    data object Settings : Screen("settings", "Settings", Icons.Filled.Settings)

    companion object {
        val bottomNavItems = listOf(Home, Assistant, Search, Files, Settings)
    }
}
