package currencyexchange.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import currencyexchange.common.NetworkState
import currencyexchange.common.toBO
import currencyexchange.data.dto.CurrenciesDTO
import currencyexchange.data.repository.CurrencyRepository
import currencyexchange.domain.bo.CurrenciesBO
import javax.inject.Inject

/**
 * Created by Tan N. Truong, on 12 April, 2023
 * Email: ludugz@gmail.com
 */
class GetCurrenciesUseCase @Inject constructor(
    private val currencyRepository: CurrencyRepository,
) {

    /**
     * Purpose of this method is to convert [CurrenciesDTO] into [CurrenciesBO]
     *
     * @param - none
     * @return -A state flow of [CurrenciesBO]
     */
    fun execute(): Flow<NetworkState<CurrenciesBO?>> {
        return currencyRepository.getCurrencies().map { dtoState ->
            when (dtoState) {
                is NetworkState.Loading -> NetworkState.Loading()
                is NetworkState.Success -> NetworkState.Success(resource = dtoState.resource?.toBO())
                is NetworkState.Failure -> NetworkState.Failure(
                    exception = dtoState.exception,
                    message = dtoState.message)
            }
        }
    }
}