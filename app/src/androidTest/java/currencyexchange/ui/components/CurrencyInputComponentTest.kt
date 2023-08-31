package currencyexchange.ui.components

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import currencyexchange.ui.components.CurrencyInputComponent
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by Tan N. Truong, on 13 April, 2023
 * Email: ludugz@gmail.com
 */

@RunWith(MockitoJUnitRunner::class)
class CurrencyInputComponentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun textField_isDisplayed() {
        composeTestRule.onNode(hasText("Input Your Currency"))
    }

    @Test
    fun whenTextField_isInput_dropDownText_displays() {
        val currencies = listOf(
            Pair("USD", "United States Dollar"),
            Pair("JPY", "Japanese Yen"),
        )
        val selectedCurrency = "USD"
        composeTestRule.setContent {
            CurrencyInputComponent(currencies = currencies) { }
        }
        val selectionText = composeTestRule.onNodeWithText("Input Your Currency")
        selectionText.performTextInput(selectedCurrency)
        val dropDownText = composeTestRule.onNodeWithText(
            text = "USD: United States Dollar",
            ignoreCase = true
        )
        dropDownText.assertIsDisplayed()
    }

    @Test
    fun whenDropDownText_performClick_textField_isUpdated() {
        val currencies = listOf(
            Pair("USD", "United States Dollar"),
            Pair("JPY", "Japanese Yen"),
        )
        val selectedCurrency = "USD"
        composeTestRule.setContent {
            CurrencyInputComponent(currencies = currencies) { }
        }
        val selectionText = composeTestRule.onNodeWithText("Input Your Currency")
        selectionText.performTextInput(selectedCurrency)
        val dropDownText = composeTestRule.onNodeWithText(
            text = "USD: United States Dollar",
            ignoreCase = true
        )
        dropDownText.performClick()
        selectionText.assertTextContains(selectedCurrency)
    }
}