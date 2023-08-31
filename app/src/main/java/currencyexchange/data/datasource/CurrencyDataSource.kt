package currencyexchange.data.datasource

import currencyexchange.data.dto.CurrenciesDTO
import currencyexchange.data.dto.ExchangeRatesDTO


/**
 * Created by Tan N. Truong, on 12 April, 2023
 * Email: ludugz@gmail.com
 */
interface CurrencyDataSource {

    suspend fun getCurrencies(): CurrenciesDTO

    suspend fun updateCurrencies(currencies: CurrenciesDTO)

    suspend fun isCurrenciesExistInDB(): Boolean

    suspend fun getExchangeRates(appId: String): ExchangeRatesDTO

    suspend fun updateExchangeRates(rates: ExchangeRatesDTO)

    suspend fun isExchangeRatesExistInDB(): Boolean

    suspend fun getLatestExchangeRateTimestamp(shortForm: String): Long

}