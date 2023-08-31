package currencyexchange.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import currencyexchange.common.Constants.EMPTY_STRING


/**
 * Jetpack Compose component that allows the user to input via TextField
 *
 * Created by Tan N. Truong, on 12 April, 2023
 * Email: ludugz@gmail.com
 */
@Composable
fun AmountInputComponent(onQueryTextListener: (Double) -> Unit) {
    var selectedAmount by remember { mutableStateOf(EMPTY_STRING) }

    OutlinedTextField(
        value = selectedAmount,
        onValueChange = { query ->
            if (!query.isDigitsOnly()) return@OutlinedTextField
            selectedAmount = query.trim()
            onQueryTextListener(if (query.isBlank()) 0.0 else query.toDouble())
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        label = { Text("Input Your Amount") },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
    )
}

@Preview
@Composable
fun PreviewAmountInputComponent() {
    AmountInputComponent {
        //no-op
    }
}