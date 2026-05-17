package com.nutrimind.ai.presentation.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Signup : Screen("signup")
    object ForgotPassword : Screen("forgot_password")
    object Onboarding : Screen("onboarding")
    object Home : Screen("home")
    object Profile : Screen("profile")
    object Scanner : Screen("scanner")
    object Chat : Screen("chat")
    object Analytics : Screen("analytics")
    object FoodTracker : Screen("food_tracker")
}
