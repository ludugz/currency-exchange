package currencyexchange.data.datasource.local

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import currencyexchange.common.toDTO
import currencyexchange.data.datasource.CurrencyDataSource
import currencyexchange.data.datasource.local.CurrencyLocalDataSourceImpl
import currencyexchange.data.datasource.local.dao.CurrencyDAO
import currencyexchange.data.datasource.local.dao.RateDAO
import currencyexchange.data.datasource.local.entities.CurrencyEntity
import currencyexchange.data.datasource.local.entities.ExchangeRateEntity
import currencyexchange.data.dto.CurrenciesDTO
import currencyexchange.data.dto.ExchangeRatesDTO

/**
 * Created by Tan N. Truong, on 12 April, 2023
 * Email: ludugz@gmail.com
 */
@RunWith(MockitoJUnitRunner::class)
class CurrencyLocalDataSourceImplTest {
    @Mock
    lateinit var currencyDAO: CurrencyDAO

    @Mock
    lateinit var rateDAO: RateDAO

    private lateinit var classUnderTest: CurrencyDataSource

    @Before
    fun setUp() {
        classUnderTest = CurrencyLocalDataSourceImpl(
            currencyDAO = currencyDAO,
            rateDAO = rateDAO
        )
    }

    @Test
    fun getCurrencies() = runBlocking {
        val expectedList = listOf(CurrencyEntity("", ""))
        `when`(currencyDAO.getAll()).thenReturn(expectedList)
        val actualDTO = classUnderTest.getCurrencies()
        assertEquals(expectedList.toDTO(), actualDTO)
    }

    @Test
    fun updateCurrencies(): Unit = runBlocking {
        val currenciesDTO = CurrenciesDTO.DEFAULT
        classUnderTest.updateCurrencies(currencies = currenciesDTO)
        verify(currencyDAO).insertAll(currencies = anyList())
    }

    @Test
    fun isCurrenciesExistInDb() = runBlocking {
        val expectedList = listOf(CurrencyEntity("", ""))
        `when`(currencyDAO.getAll()).thenReturn(expectedList)
        assertEquals(expectedList.isNotEmpty(), classUnderTest.isCurrenciesExistInDB())
    }

    @Test
    fun getExchangeRates() = runBlocking {
        val expectedList = listOf(ExchangeRateEntity("", 0L, 0.0, ""))
        `when`(rateDAO.getAll()).thenReturn(expectedList)
        val actualDTO = classUnderTest.getExchangeRates(appId = "")
        assertEquals(expectedList.toDTO(), actualDTO)
    }

    @Test
    fun updateExchangeRates(): Unit = runBlocking {
        val ratesDTO = ExchangeRatesDTO.DEFAULT
        classUnderTest.updateExchangeRates(rates = ratesDTO)
        verify(rateDAO).insertAll(rates = anyList())
    }

    @Test
    fun isExchangeRatesExistInDB() = runBlocking {
        val expectedList = listOf(ExchangeRateEntity("", 0L, 0.0, ""))
        `when`(rateDAO.getAll()).thenReturn(expectedList)
        assertEquals(expectedList.isNotEmpty(), classUnderTest.isExchangeRatesExistInDB())
    }

    @Test
    fun getLatestExchangeRateTimestamp(): Unit = runBlocking {
        val dummyShortForm = "DUMMY"
        val expectedTimestamp = 1L
        `when`(rateDAO.getTimestamp(anyString())).thenReturn(expectedTimestamp)
        val actualTimestamp = classUnderTest.getLatestExchangeRateTimestamp(dummyShortForm)
        assertEquals(expectedTimestamp, actualTimestamp)
    }
}