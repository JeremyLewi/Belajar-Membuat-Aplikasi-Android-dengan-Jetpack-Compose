package com.example.jetgarage.ui.screen.detail

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.example.jetgarage.model.Car
import com.example.jetgarage.model.OrderCar
import com.example.jetgarage.ui.theme.JetGarageTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import com.example.jetgarage.R
import com.example.jetgarage.onNodeWithStringId

class DetailContentTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val fakeOrderCar = OrderCar(
        car = Car(
            1,
            R.drawable.car_1,
            "Toyota Corolla",
            20000,
            "The Toyota Corolla is a line of subcompact and compact cars manufactured by Toyota. Introduced in 1966, the Corolla was the best-selling car worldwide by 1974 and has been one of the best-selling cars in the world since then."
        ),
        count = 0
    )

    @Before
    fun setUp() {
        composeTestRule.setContent {
            JetGarageTheme {
                DetailContent(
                    fakeOrderCar.car.image,
                    fakeOrderCar.car.title,
                    fakeOrderCar.car.requiredPrice,
                    fakeOrderCar.car.description,
                    fakeOrderCar.count,
                    onBackClick = {},
                    onAddToCart = {}
                )
            }
        }
        composeTestRule.onRoot().printToLog("currentLabelExists")
    }

    @Test
    fun detailContent_isDisplayed() {
        composeTestRule.onNodeWithText(fakeOrderCar.car.title).assertIsDisplayed()
        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(
                R.string.required_price,
                fakeOrderCar.car.requiredPrice
            )
        ).assertIsDisplayed()
        composeTestRule.onNodeWithText(fakeOrderCar.car.description).assertIsDisplayed()
    }

    @Test
    fun increaseProduct_buttonEnabled() {
        composeTestRule.onNodeWithContentDescription("Order Button").assertIsNotEnabled()
        composeTestRule.onNodeWithStringId(R.string.plus_symbol).performClick()
        composeTestRule.onNodeWithContentDescription("Order Button").assertIsEnabled()
    }

    @Test
    fun increaseProduct_correctCounter() {
        composeTestRule.onNodeWithStringId(R.string.plus_symbol).performClick().performClick()
        composeTestRule.onNodeWithTag("count").assert(hasText("2"))
    }

    @Test
    fun decreaseProduct_stillZero() {
        composeTestRule.onNodeWithStringId(R.string.minus_symbol).performClick()
        composeTestRule.onNodeWithTag("count").assert(hasText("0"))
    }
}