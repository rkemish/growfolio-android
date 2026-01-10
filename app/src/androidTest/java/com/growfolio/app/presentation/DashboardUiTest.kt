package com.growfolio.app.presentation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.growfolio.app.domain.model.Portfolio
import com.growfolio.app.presentation.dashboard.DashboardContent
import com.growfolio.app.ui.theme.GrowfolioTheme
import org.junit.Rule
import org.junit.Test

class DashboardUiTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun dashboardContent_showsTotalBalance() {
        val totalValue = 12345.67
        val portfolios = listOf(
            Portfolio(
                id = "1",
                userId = "u1",
                name = "Growth",
                totalValue = 5000.0,
                createdAt = "",
                updatedAt = ""
            )
        )

        composeTestRule.setContent {
            GrowfolioTheme {
                DashboardContent(
                    totalValue = totalValue,
                    portfolios = portfolios
                )
            }
        }

        composeTestRule.onNodeWithText("Total Balance").assertIsDisplayed()
        composeTestRule.onNodeWithText("$12,345.67").assertIsDisplayed()
        composeTestRule.onNodeWithText("Growth").assertIsDisplayed()
    }
}
