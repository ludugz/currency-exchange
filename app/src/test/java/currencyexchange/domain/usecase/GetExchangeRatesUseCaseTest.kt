package currencyexchange.domain.usecase

import currencyexchange.domain.usecase.GetExchangeRatesUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import currencyexchange.BuildConfig.API_KEY
import currencyexchange.common.NetworkState
import currencyexchange.data.dto.ExchangeRatesDTO
import currencyexchange.data.repository.CurrencyRepository

/**
 * Created by Tan N. Truong, on 13 April, 2023
 * Email: ludugz@gmail.com
 */
@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class GetExchangeRatesUseCaseTest {

    @Mock
    lateinit var currencyRepository: CurrencyRepository

    @Mock
    lateinit var loadingState: NetworkState.Loading<ExchangeRatesDTO>

    @Mock
    lateinit var successState: NetworkState.Success<ExchangeRatesDTO>

    @Mock
    lateinit var failureState: NetworkState.Failure<ExchangeRatesDTO>

    private lateinit var classUnderTest: GetExchangeRatesUseCase

    @Before
    fun setUp() {
        classUnderTest = GetExchangeRatesUseCase(currencyRepository = currencyRepository)
    }

    @Test
    fun `test number of flows when succeeds`() = runTest {
        // Given
        val expectedFlowCount = 2 // LOADING & SUCCESS
        val successFlow = flowOf(loadingState, successState)
        `when`(currencyRepository.getExchangeRates(appId = API_KEY)).thenReturn(successFlow)

        // Execute
        val actualFlowCount = classUnderTest.execute().count()

        // Validate
        assertEquals(expectedFlowCount, actualFlowCount)
    }

    @Test
    fun `test number of flows when succeeds in exact order`() = runTest {
        // Given
        val successFlow = flowOf(loadingState, successState)
        `when`(currencyRepository.getExchangeRates(appId = API_KEY)).thenReturn(successFlow)

        // Execute
        val actualFlow = classUnderTest.execute()

        // Validate
        assert(actualFlow.first() is NetworkState.Loading)
        assert(actualFlow.drop(1).first() is NetworkState.Success)
    }

    @Test
    fun `test number of flows when failure`() = runTest {
        // Given
        val expectedFlowCount = 2 // LOADING & FAILURE
        `when`(failureState.message).thenReturn("Failure message")
        val failureFlow = flowOf(loadingState, failureState)
        `when`(currencyRepository.getExchangeRates(appId = API_KEY)).thenReturn(failureFlow)

        // Execute
        val actualFlowCount = classUnderTest.execute().count()

        // Validate
        assertEquals(expectedFlowCount, actualFlowCount)
    }

    @Test
    fun `test number of flows when failure in exact order`() = runTest {
        // Given
        val failureFlow = flowOf(loadingState, failureState)
        `when`(failureState.message).thenReturn("Failure message")
        `when`(currencyRepository.getExchangeRates(appId = API_KEY)).thenReturn(failureFlow)

        // Execute
        val actualFlow = classUnderTest.execute()

        // Validate
        assert(actualFlow.first() is NetworkState.Loading)
        assert(actualFlow.drop(1).first() is NetworkState.Failure)
    }
}