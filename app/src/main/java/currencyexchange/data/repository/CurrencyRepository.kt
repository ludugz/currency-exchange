package currencyexchange.data.repository

import kotlinx.coroutines.flow.Flow
import currencyexchange.common.NetworkState
import currencyexchange.data.dto.CurrenciesDTO
import currencyexchange.data.dto.ExchangeRatesDTO


/**
 * Created by Tan N. Truong, on 12 April, 2023
 * Email: ludugz@gmail.com
 */
interface CurrencyRepository {
    fun getCurrencies(): Flow<NetworkState<CurrenciesDTO>>

    fun getExchangeRates(appId: String): Flow<NetworkState<ExchangeRatesDTO>>
}