package currencyexchange.ui.pages

import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.runBlocking
import currencyexchange.common.Constants.EMPTY_STRING
import currencyexchange.common.NetworkState
import currencyexchange.common.UIState
import currencyexchange.domain.bo.CurrenciesBO
import currencyexchange.domain.bo.ExchangeRatesBO
import currencyexchange.domain.usecase.GetCurrenciesUseCase
import currencyexchange.domain.usecase.GetExchangeRatesUseCase
import javax.inject.Inject


/**
 * Created by Tan N. Truong, on 12 April, 2023
 * Email: ludugz@gmail.com
 */
@HiltViewModel
class CurrencyExchangeViewModel @Inject constructor(
    private val getCurrenciesUseCase: GetCurrenciesUseCase,
    private val getExchangeRatesUseCase: GetExchangeRatesUseCase,
) : ViewModel() {

    var currenciesState by mutableStateOf(value = UIState(resource = CurrenciesBO.DEFAULT))
        private set

    var usdBasedRatesState by mutableStateOf<UIState<ExchangeRatesBO>>(value = UIState())
        private set

    var certainCurrencyRateState by mutableStateOf(value = ExchangeRatesBO.DEFAULT)
        private set

    var amount: Double by mutableStateOf(1.0)

    var selectedCurrency: String by mutableStateOf(EMPTY_STRING)

    init {
        val something = runBlocking {
            fetchCurrencies()
        }
    }

    /**
     * Fetch currencies from Database or Server and update to [currenciesState]
     *
     * @param - none
     * @return - Unit
     */
    suspend fun fetchCurrencies() {
        getCurrenciesUseCase.execute().collectLatest { networkState ->
            currenciesState = when (networkState) {
                is NetworkState.Loading -> UIState.Loading()
                is NetworkState.Success -> UIState.Success(resource = networkState.resource)
                is NetworkState.Failure -> UIState.Failure(
                    exception = networkState.exception,
                    message = networkState.message
                )
            }
        }
    }

    /**
     * Fetch exchange rates from Database or Server and update to [usdBasedRatesState]
     * My current plan only allows me to select "USD" as base currency.
     * Therefore, all of the fetched rates are based on USD.
     *
     * @param - none
     * @return - Unit
     */
    suspend fun fetchLatestRates() {
        getExchangeRatesUseCase.execute().collectLatest { state ->
            usdBasedRatesState = when (state) {
                is NetworkState.Success -> {
                    UIState.Success(resource = state.resource)
                }

                is NetworkState.Failure -> {
                    UIState.Failure(exception = state.exception, message = state.message)
                }

                is NetworkState.Loading -> {
                    UIState.Loading()
                }
            }
        }
    }

    /**
     * Based on rates [usdBasedRatesState] received from API, calculate other
     * currency and update to [certainCurrencyRateState]
     * @pram - none
     * @return - Unit
     */
    fun updateCertainCurrencyRateBasedOnUSDRate() {
        val usdRates = usdBasedRatesState.resource?.rates.orEmpty()
        val amount = amount
        certainCurrencyRateState =
            ExchangeRatesBO(
                baseCurrency = selectedCurrency,
                amount = amount,
                rates = convertUsdToOtherCurrency(
                    amount = amount,
                    usdBasedRates = usdRates,
                    baseCurrency = selectedCurrency
                )
            )
    }

    @VisibleForTesting
    internal fun convertUsdToOtherCurrency(
        amount: Double,
        usdBasedRates: Map<String, Double>,
        baseCurrency: String,
    ): Map<String, Double> {
        try {
            val rate = amount / (usdBasedRates[baseCurrency])!!
            return usdBasedRates.map { item -> Pair(item.key, rate * item.value) }.toMap()
        } catch (exception: Exception) {
            Log.e(TAG, "Failed when converting rates: $exception")
            throw Exception()
        }
    }

    companion object {

        const val TAG = "CurrencyExchangeViewModel"
    }
}