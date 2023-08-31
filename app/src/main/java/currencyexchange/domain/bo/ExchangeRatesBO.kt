package currencyexchange.domain.bo

import currencyexchange.common.Constants.DEFAULT_CURRENCY_AMOUNT
import currencyexchange.common.Constants.DEFAULT_CURRENCY_BASE_CURRENCY


/**
 * Exchange rates Business Object to display in UI
 *
 * Created by Tan N. Truong, on 12 April, 2023
 * Email: ludugz@gmail.com
 */
data class ExchangeRatesBO(
    val baseCurrency: String = DEFAULT_CURRENCY_BASE_CURRENCY,
    val amount: Double = DEFAULT_CURRENCY_AMOUNT,
    val rates: Map<String, Double> = emptyMap(),
) {

    companion object {
        val DEFAULT = ExchangeRatesBO()
    }
}
