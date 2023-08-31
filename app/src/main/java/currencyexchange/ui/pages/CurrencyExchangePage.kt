package currencyexchange.ui.pages

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import currencyexchange.common.UIState
import currencyexchange.ui.components.AmountInputComponent
import currencyexchange.ui.components.CurrenciesListComponent
import currencyexchange.ui.components.CurrencyInputComponent

/**
 * Created by Tan N. Truong, on 12 April, 2023
 * Email: ludugz@gmail.com
 */
@Composable
fun CurrencyExchangePage(
    viewModel: CurrencyExchangeViewModel = hiltViewModel(),
) {

    var isAmountDisplayed by remember { mutableStateOf(false) }

    var isCurrencyDisplayed by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            with(viewModel) {
                fetchCurrencies()
                fetchLatestRates()
            }
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        if ((viewModel.currenciesState is UIState.Loading) || (viewModel.usdBasedRatesState is UIState.Loading)) {
            Log.d("CurrencyExchangePage", "UI STATE: Loading")
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }

        when (viewModel.usdBasedRatesState) {
            is UIState.Success -> {
                Log.d("CurrencyExchangePage", "UI STATE: Success")
                val currencies = viewModel.currenciesState.resource?.currencies?.toList()
                Column(modifier = Modifier
                    .fillMaxWidth()
                ) {
                    AmountInputComponent { query ->
                        isAmountDisplayed = true
                        viewModel.amount = query
                    }

                    CurrencyInputComponent(
                        currencies = currencies
                    ) { selectedCurrency ->
                        isCurrencyDisplayed = true
                        viewModel.selectedCurrency = selectedCurrency
                    }

                    // Only display Currency Symbol List when the user has already input amount & selected currency
                    if (isAmountDisplayed && isCurrencyDisplayed) {
                        viewModel.updateCertainCurrencyRateBasedOnUSDRate()
                        CurrenciesListComponent(
                            rates = viewModel.certainCurrencyRateState.rates.toList()
                        )
                    }
                }
            }

            is UIState.Failure -> {
                Log.d("CurrencyExchangePage", "UI STATE: Failure")
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center),
                    text = viewModel.usdBasedRatesState.message.orEmpty(),
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center)
            }
        }
    }
}

@Preview
@Composable
fun PreviewCurrencyExchangePage() {
    CurrencyExchangePage()
}