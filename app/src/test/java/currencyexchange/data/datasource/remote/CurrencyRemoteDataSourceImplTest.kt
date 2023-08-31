package currencyexchange.data.datasource.remote

import currencyexchange.data.datasource.remote.ApiClient
import currencyexchange.data.datasource.remote.CurrencyRemoteDataSourceImpl
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import currencyexchange.data.datasource.CurrencyDataSource
import currencyexchange.data.dto.CurrenciesDTO
import currencyexchange.data.dto.ExchangeRatesDTO

/**
 * Created by Tan N. Truong, on 12 April, 2023
 * Email: ludugz@gmail.com
 */
@RunWith(MockitoJUnitRunner::class)
class CurrencyRemoteDataSourceImplTest {

    @Mock
    lateinit var apiClient: ApiClient

    private lateinit var classUnderTest: CurrencyDataSource

    @Before
    fun setUp() {
        classUnderTest = CurrencyRemoteDataSourceImpl(apiClient = apiClient)
    }

    @Test
    fun getCurrencies(): Unit = runBlocking {
        val dummyMap = mapOf(Pair("Dummy", "Dummy"))
        val expectedDTO = CurrenciesDTO(currencies = dummyMap)
        `when`(apiClient.getCurrencies()).thenReturn(dummyMap)
        val actualDTO = classUnderTest.getCurrencies()
        assertEquals(expectedDTO, actualDTO)
    }

    @Test
    fun getExchangeRates() = runBlocking {
        val expectedDTO = mock(ExchangeRatesDTO::class.java)
        `when`(apiClient.getExchangeRates(anyString())).thenReturn(expectedDTO)
        val actualDTO = classUnderTest.getExchangeRates("DUMMY APP ID")
        assertEquals(expectedDTO, actualDTO)
    }
}