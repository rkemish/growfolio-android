package com.growfolio.app.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val label: String, val icon: ImageVector) {
    object Dashboard : Screen("dashboard", "Dashboard", Icons.Default.Dashboard)
    object Stocks : Screen("stocks", "Stocks", Icons.Default.ShowChart)
    object Funding : Screen("funding", "Funding", Icons.Default.AccountBalanceWallet)
    object AIInsights : Screen("ai_insights", "AI Insights", Icons.Default.Analytics)
    object Settings : Screen("settings", "Settings", Icons.Default.Settings)
}

val bottomNavItems = listOf(
    Screen.Dashboard,
    Screen.Stocks,
    Screen.Funding,
    Screen.AIInsights,
    Screen.Settings
)
