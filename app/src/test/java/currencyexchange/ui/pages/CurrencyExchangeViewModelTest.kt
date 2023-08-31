package currencyexchange.ui.pages

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import currencyexchange.domain.usecase.GetCurrenciesUseCase
import currencyexchange.domain.usecase.GetExchangeRatesUseCase
import currencyexchange.ui.pages.CurrencyExchangeViewModel

/**
 * Created by Tan N. Truong, on 12 April, 2023
 * Email: ludugz@gmail.com
 */
@RunWith(MockitoJUnitRunner::class)
class CurrencyExchangeViewModelTest {
    @Mock
    lateinit var getCurrenciesUseCase: GetCurrenciesUseCase

    @Mock
    lateinit var getExchangeRatesUseCase: GetExchangeRatesUseCase

    private lateinit var classUnderTest: CurrencyExchangeViewModel

    @Before
    fun setUp() {
        classUnderTest = CurrencyExchangeViewModel(
            getCurrenciesUseCase = getCurrenciesUseCase,
            getExchangeRatesUseCase = getExchangeRatesUseCase
        )
    }

    @Test
    fun `conversion between USD based rate and other currencies`() {
        val aedToUSDrate = 3.67
        val afnToUSDrate = 86.86
        val jpyToUSDrate = 0.01

        val usdRates = mapOf(
            Pair("AED", aedToUSDrate),
            Pair("AFN", afnToUSDrate),
            Pair("JPY", jpyToUSDrate)
        )

        val baseCurrency = "JPY"
        val amount = 5.0

        val expectedJPYrates = mapOf(
            Pair("AED", amount / jpyToUSDrate * aedToUSDrate),
            Pair("AFN", amount / jpyToUSDrate * afnToUSDrate),
            Pair("JPY", amount / jpyToUSDrate * jpyToUSDrate),
        )
        val actualResult = classUnderTest.convertUsdToOtherCurrency(amount = amount,
            usdBasedRates = usdRates,
            baseCurrency = baseCurrency)
        Assert.assertEquals(expectedJPYrates, actualResult)
    }
}