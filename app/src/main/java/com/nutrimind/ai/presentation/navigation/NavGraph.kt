package com.nutrimind.ai.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nutrimind.ai.presentation.screens.OnboardingScreen
import com.nutrimind.ai.presentation.screens.MainScreen
import com.nutrimind.ai.presentation.screens.auth.LoginScreen
import com.nutrimind.ai.presentation.screens.auth.SignupScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String = Screen.Onboarding.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                onNavigateToSignup = { navController.navigate(Screen.Signup.route) },
                onNavigateToHome = {
                    navController.navigate(Screen.Onboarding.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.Signup.route) {
            SignupScreen(
                onNavigateToLogin = { navController.navigate(Screen.Login.route) },
                onNavigateToHome = {
                    navController.navigate(Screen.Onboarding.route) {
                        popUpTo(Screen.Signup.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.Onboarding.route) {
            OnboardingScreen(onFinished = {
                navController.navigate("profile_setup") {
                    popUpTo(Screen.Onboarding.route) { inclusive = true }
                }
            })
        }
        composable("profile_setup") {
            com.nutrimind.ai.presentation.screens.profile.ProfileSetupScreen(onFinished = {
                navController.navigate("main") {
                    popUpTo("profile_setup") { inclusive = true }
                }
            })
        }
        composable("main") {
            MainScreen()
        }
        composable(Screen.FoodTracker.route) {
            com.nutrimind.ai.presentation.screens.AddFoodScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
