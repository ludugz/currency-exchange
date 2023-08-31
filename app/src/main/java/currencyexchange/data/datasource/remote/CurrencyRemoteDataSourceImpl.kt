package currencyexchange.data.datasource.remote

import currencyexchange.common.Constants.UNDEFINED_TIME_STAMP
import currencyexchange.data.datasource.CurrencyDataSource
import currencyexchange.data.dto.CurrenciesDTO
import currencyexchange.data.dto.ExchangeRatesDTO
import javax.inject.Inject


/**
 * Created by Tan N. Truong, on 12 April, 2023
 * Email: ludugz@gmail.com
 */
class CurrencyRemoteDataSourceImpl @Inject constructor(
    private val apiClient: ApiClient,
) : CurrencyDataSource {
    override suspend fun getCurrencies(): CurrenciesDTO {
        return CurrenciesDTO(currencies = apiClient.getCurrencies())
    }

    override suspend fun updateCurrencies(currencies: CurrenciesDTO) {
        //no-op
    }

    override suspend fun isCurrenciesExistInDB(): Boolean {
        //no-op
        return false
    }

    override suspend fun getExchangeRates(appId: String): ExchangeRatesDTO {
        return apiClient.getExchangeRates(appId = appId)
    }

    override suspend fun updateExchangeRates(rates: ExchangeRatesDTO) {
        //no-op
    }

    override suspend fun isExchangeRatesExistInDB(): Boolean {
        //no-op
        return false
    }

    override suspend fun getLatestExchangeRateTimestamp(shortForm: String): Long {
        //no-op
        return UNDEFINED_TIME_STAMP
    }
}