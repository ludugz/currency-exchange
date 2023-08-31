package currencyexchange.common

import currencyexchange.common.toBO
import currencyexchange.common.toDTO
import currencyexchange.common.toEntity
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
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
@RunWith(JUnit4::class)
class ConverterTest {

    @Test
    fun `Currencies DTO to BO`() {
        val dto = dummyCurrenciesDTO()
        val bo = dummyCurrenciesBO()
        assertEquals(bo, dto.toBO())
    }

    @Test
    fun `Rates DTO to BO`() {
        val dto = createDummyExchangeRatesDTO()
        val bo = dummyExchangeRatesBO()
        assertEquals(bo, dto.toBO())
    }

    @Test
    fun `Currencies entity to DTO`() {
        val entity = dummyCurrencyEntity()
        val dto = dummyCurrenciesDTO()
        assertEquals(dto, entity.toDTO())
    }

    @Test
    fun `Rates entity to DTO`() {
        val entity = createDummyExchangeRatesEntity()
        val dto = createDummyExchangeRatesDTO()
        assertEquals(dto, entity.toDTO())
    }

    @Test
    fun `Currencies DTO to entity`() {
        val dto = dummyCurrenciesDTO()
        val entity = dummyCurrencyEntity()
        assertEquals(entity, dto.toEntity())
    }

    @Test
    fun `Rates DTO to entity`() {
        val dto = createDummyExchangeRatesDTO()
        val entity = createDummyExchangeRatesEntity()
        assertEquals(entity, dto.toEntity())
    }

    private fun createDummyExchangeRatesDTO() = ExchangeRatesDTO(
        timestamp = DUMMY_TIME_STAMP,
        baseCurrency = DUMMY_BASE_CURRENCY,
        rates = mapOf(Pair(first = DUMMY_BASE_CURRENCY, second = DUMMY_VALUE)),
    )

    private fun createDummyExchangeRatesEntity() = listOf(
        ExchangeRateEntity(
        shortForm = DUMMY_BASE_CURRENCY,
        timestamp = DUMMY_TIME_STAMP,
        value = DUMMY_VALUE,
        baseCurrency = DUMMY_BASE_CURRENCY,
    )
    )

    private fun dummyCurrenciesDTO() =
        CurrenciesDTO(mapOf(Pair(DUMMY_SHORT_FORM, DUMMY_LONG_FORM)))

    private fun dummyCurrenciesBO() =
        CurrenciesBO(currencies = mapOf(Pair(DUMMY_SHORT_FORM, DUMMY_LONG_FORM)))

    private fun dummyExchangeRatesBO() = ExchangeRatesBO(
        baseCurrency = DUMMY_BASE_CURRENCY,
        rates = mapOf(Pair(DUMMY_BASE_CURRENCY, DUMMY_VALUE)),
    )

    private fun dummyCurrencyEntity() =
        listOf(CurrencyEntity(shortForm = DUMMY_SHORT_FORM, longForm = DUMMY_LONG_FORM))


    companion object {
        const val DUMMY_SHORT_FORM = "USD"
        const val DUMMY_LONG_FORM = "LONG FORM"
        const val DUMMY_TIME_STAMP = 1L
        const val DUMMY_BASE_CURRENCY = "JPY"
        const val DUMMY_VALUE = 1.0
    }
}