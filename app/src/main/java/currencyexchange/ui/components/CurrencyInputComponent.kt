package currencyexchange.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import currencyexchange.common.Constants.EMPTY_STRING
import currencyexchange.ui.theme.DropDownBackground


/**
 * Jetpack Compose component that allows the user to input currency to be converted
 * Created by Tan N. Truong, on 12 April, 2023
 * Email: ludugz@gmail.com
 */
@Composable
fun CurrencyInputComponent(
    currencies: List<Pair<String, String>>?,
    onItemClicked: (String) -> Unit,
) {

    var expanded by remember { mutableStateOf(false) }

    var selectedItem by remember { mutableStateOf(EMPTY_STRING) }

    val filteredList = currencies.orEmpty().filter { currency ->
        currency.first.contains(
            other = selectedItem,
            ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth()
    ) {
        OutlinedTextField(
            value = selectedItem,
            onValueChange = {
                selectedItem = it
                expanded = true
            },
            modifier = Modifier
                .fillMaxWidth(),
            label = { Text("Input Your Currency") },
            trailingIcon = {
                Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = null,
                    Modifier.clickable { expanded = !expanded })
            }
        )

        AnimatedVisibility(visible = expanded) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(DropDownBackground)
                    .clickable { expanded = false },
            ) {
                items(filteredList) { currency ->
                    Text(
                        text = "${currency.first}: ${currency.second}",
                        modifier = Modifier
                            .height(70.dp)
                            .fillMaxWidth()
                            .padding(10.dp)
                            .clickable {
                                expanded = false
                                selectedItem = currency.first
                                onItemClicked(currency.first)
                            },
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewCurrencyInputComponent() {
    val previewCurrencies = listOf(
        Pair(first = "USD", second = "US Dollar"),
        Pair(first = "JPY", second = "Japanese YEN"),
    )
    CurrencyInputComponent(currencies = previewCurrencies) {
        //no-op
    }
}