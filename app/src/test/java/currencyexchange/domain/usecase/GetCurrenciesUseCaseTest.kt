package currencyexchange.domain.usecase

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import currencyexchange.common.NetworkState
import currencyexchange.data.dto.CurrenciesDTO
import currencyexchange.domain.usecase.GetCurrenciesUseCase
import currencyexchange.data.repository.CurrencyRepository

/**
 * Created by Tan N. Truong, on 13 April, 2023
 * Email: ludugz@gmail.com
 */
@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class GetCurrenciesUseCaseTest {

    @Mock
    lateinit var currencyRepository: CurrencyRepository

    @Mock
    lateinit var loadingState: NetworkState.Loading<CurrenciesDTO>

    @Mock
    lateinit var successState: NetworkState.Success<CurrenciesDTO>

    @Mock
    lateinit var failureState: NetworkState.Failure<CurrenciesDTO>

    private lateinit var classUnderTest: GetCurrenciesUseCase

    @Before
    fun setUp() {
        classUnderTest = GetCurrenciesUseCase(currencyRepository = currencyRepository)
    }

    @Test
    fun `test number of flows when succeeds`() = runTest {
        // Given
        val expectedFlowCount = 2 // LOADING & SUCCESS
        val successFlow = flowOf(loadingState, successState)
        `when`(currencyRepository.getCurrencies()).thenReturn(successFlow)

        // Execute
        val actualFlowCount = classUnderTest.execute().count()

        // Validate
        assertEquals(expectedFlowCount, actualFlowCount)
    }

    @Test
    fun `test number of flows when succeeds in exact order`() = runBlocking {
        // Given
        val successFlow = flowOf(loadingState, successState)
        `when`(currencyRepository.getCurrencies()).thenReturn(successFlow)

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
        `when`(currencyRepository.getCurrencies()).thenReturn(failureFlow)

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
        `when`(currencyRepository.getCurrencies()).thenReturn(failureFlow)

        // Execute
        val actualFlow = classUnderTest.execute()

        // Validate
        assert(actualFlow.first() is NetworkState.Loading)
        assert(actualFlow.drop(1).first() is NetworkState.Failure)
    }
}