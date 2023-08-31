package currencyexchange.ui.components

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview


/**
 * Jetpack Compose component to display a list of [CurrencyInputComponent]
 *
 * Created by Tan N. Truong, on 12 April, 2023
 * Email: ludugz@gmail.com
 */

const val GRID_CELL_NUMBER = 2

@Composable
fun CurrenciesListComponent(rates: List<Pair<String, Double>>) {
    LazyVerticalGrid(columns = GridCells.Fixed(GRID_CELL_NUMBER)) {
        items(rates) { currency ->
            CurrencyItemComponent(currency = currency.first, amount = currency.second)
        }
    }
}

@Preview
@Composable
fun PreviewCurrenciesListComponent() {
    CurrenciesListComponent(rates = listOf(Pair("USD", 1.0), Pair("JPY", 2.0)))
}