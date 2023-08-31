package currencyexchange.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import currencyexchange.ui.theme.DropDownBackground


/**
 * Jetpack Compose component to display one currency item (with symbol and value)
 *
 * Created by Tan N. Truong, on 12 April, 2023
 * Email: ludugz@gmail.com
 */
@Composable
fun CurrencyItemComponent(
    modifier: Modifier = Modifier,
    currency: String,
    amount: Double,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(modifier = Modifier
            .size(size = 90.dp)
            .clip(shape = CircleShape)
            .background(DropDownBackground)
        )
        {
            Text(
                text = currency,
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }
        Text(
            text = String.format("%.2f", amount),
            color = Color.Blue,
        )
    }
}

@Preview
@Composable
fun PreviewCurrencyItemComponent() {
    CurrencyItemComponent(currency = "USD", amount = 5.0)
}