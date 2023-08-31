package currencyexchange.data.datasource.local

import currencyexchange.common.toDTO
import currencyexchange.common.toEntity
import currencyexchange.data.datasource.CurrencyDataSource
import currencyexchange.data.datasource.local.dao.CurrencyDAO
import currencyexchange.data.datasource.local.dao.RateDAO
import currencyexchange.data.dto.CurrenciesDTO
import currencyexchange.data.dto.ExchangeRatesDTO
import javax.inject.Inject


/**
 * Created by Tan N. Truong, on 12 April, 2023
 * Email: ludugz@gmail.com
 */
class CurrencyLocalDataSourceImpl @Inject constructor(
    private val currencyDAO: CurrencyDAO,
    private val rateDAO: RateDAO,
) : CurrencyDataSource {
    override suspend fun getCurrencies(): CurrenciesDTO {
        return currencyDAO.getAll().toDTO()
    }

    override suspend fun updateCurrencies(currencies: CurrenciesDTO) {
        currencyDAO.insertAll(currencies = currencies.toEntity())
    }

    override suspend fun isCurrenciesExistInDB(): Boolean {
        return currencyDAO.getAll().isNotEmpty()
    }

    override suspend fun getExchangeRates(appId: String): ExchangeRatesDTO {
        return rateDAO.getAll().toDTO()
    }

    override suspend fun updateExchangeRates(rates: ExchangeRatesDTO) {
        rateDAO.insertAll(rates = rates.toEntity())
    }

    override suspend fun isExchangeRatesExistInDB(): Boolean {
        return rateDAO.getAll().isNotEmpty()
    }

    override suspend fun getLatestExchangeRateTimestamp(shortForm: String): Long {
        return rateDAO.getTimestamp(shortForm = shortForm)
    }
}