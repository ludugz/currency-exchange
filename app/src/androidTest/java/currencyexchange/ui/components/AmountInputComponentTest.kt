package currencyexchange.ui.components

import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import currencyexchange.ui.components.AmountInputComponent
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by Tan N. Truong, on 13 April, 2023
 * Email: ludugz@gmail.com
 */
@RunWith(MockitoJUnitRunner::class)
class AmountInputComponentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun textField_isDisplayed() {
        composeTestRule.onNode(hasText("Input Your Amount"))
    }

    @Test
    fun whenTextField_isInput_query_isUpdated() {
        var actualAmount = 0.0
        composeTestRule.setContent {
            AmountInputComponent { amount ->
                actualAmount = amount
            }
        }
        val query = "123"
        val textField = composeTestRule.onNodeWithText("Input Your Amount")
        textField.performTextInput(query)
        assert(query.toDouble() == actualAmount)
    }
}