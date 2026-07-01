package com.rajkrishan.rajjarvis.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rajkrishan.rajjarvis.ui.assistant.AssistantScreen
import com.rajkrishan.rajjarvis.ui.files.FilesScreen
import com.rajkrishan.rajjarvis.ui.home.HomeScreen
import com.rajkrishan.rajjarvis.ui.search.SearchScreen
import com.rajkrishan.rajjarvis.ui.settings.SettingsScreen
import com.rajkrishan.rajjarvis.ui.splash.SplashScreen

@Composable
fun RajJarvisNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
        enterTransition = { fadeIn(tween(350)) },
        exitTransition = { fadeOut(tween(250)) }
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(onFinished = {
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Splash.route) { inclusive = true }
                }
            })
        }
        composable(Screen.Home.route) { HomeScreen(navController) }
        composable(Screen.Assistant.route) { AssistantScreen() }
        composable(Screen.Search.route) { SearchScreen() }
        composable(Screen.Files.route) { FilesScreen() }
        composable(Screen.Settings.route) { SettingsScreen() }
    }
}
