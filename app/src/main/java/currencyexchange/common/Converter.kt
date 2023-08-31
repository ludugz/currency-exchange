package currencyexchange.common

import currencyexchange.data.datasource.local.entities.CurrencyEntity
import currencyexchange.data.datasource.local.entities.ExchangeRateEntity
import currencyexchange.data.dto.CurrenciesDTO
import currencyexchange.data.dto.ExchangeRatesDTO
import currencyexchange.domain.bo.CurrenciesBO
import currencyexchange.domain.bo.ExchangeRatesBO

/**
 * Created by Tan N. Truong, on 12 April, 2023
 * Email: ludugz@gmail.com
 */
fun CurrenciesDTO.toBO(): CurrenciesBO {
    return CurrenciesBO(
        currencies = currencies.orEmpty(),
    )
}


fun ExchangeRatesDTO.toBO(): ExchangeRatesBO {
    return ExchangeRatesBO(
        baseCurrency = baseCurrency.orEmpty(),
        rates = rates.orEmpty()
    )
}

fun List<CurrencyEntity>.toDTO(): CurrenciesDTO {
    return CurrenciesDTO(currencies = this.associate { currency ->
        Pair(currency.shortForm,
            currency.longForm)
    }
    )
}

fun List<ExchangeRateEntity>.toDTO(): ExchangeRatesDTO {
    val baseCurrency = this.first().baseCurrency
    val timeStamp = this.first().timestamp
    val rates = this.associate { Pair(it.shortForm, it.value) }
    return ExchangeRatesDTO(
        baseCurrency = baseCurrency,
        timestamp = timeStamp,
        rates = rates
    )
}

fun CurrenciesDTO.toEntity(): List<CurrencyEntity> {
    return this.currencies.orEmpty().toList()
        .map { pair -> CurrencyEntity(shortForm = pair.first, longForm = pair.second) }
}

fun ExchangeRatesDTO.toEntity(): List<ExchangeRateEntity> {
    return this.rates?.toList()?.map { pair ->
        ExchangeRateEntity(shortForm = pair.first,
            timestamp = this.timestamp,
            value = pair.second,
            baseCurrency = baseCurrency.orEmpty())
    }.orEmpty()
}