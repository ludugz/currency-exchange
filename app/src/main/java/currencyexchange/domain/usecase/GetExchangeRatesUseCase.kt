package currencyexchange.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import currencyexchange.BuildConfig
import currencyexchange.common.NetworkState
import currencyexchange.common.toBO
import currencyexchange.data.repository.CurrencyRepository
import currencyexchange.domain.bo.ExchangeRatesBO
import javax.inject.Inject


/**
 * Created by Tan N. Truong, on 12 April, 2023
 * Email: ludugz@gmail.com
 */
class GetExchangeRatesUseCase @Inject constructor(
    private val currencyRepository: CurrencyRepository,
) {

    /**
     * Purpose of this method is to convert [ExchangeRatesBO] into [ExchangeRatesBO]
     *
     * @param - none
     * @return -A state flow of [ExchangeRatesBO]
     */
    fun execute(): Flow<NetworkState<ExchangeRatesBO?>> {
        return currencyRepository.getExchangeRates(appId = BuildConfig.API_KEY)
            .map { dtoState ->
                when (dtoState) {
                    is NetworkState.Loading -> NetworkState.Loading()
                    is NetworkState.Success -> {
                        NetworkState.Success(dtoState.resource?.toBO())
                    }
                    is NetworkState.Failure -> NetworkState.Failure(exception = dtoState.exception,
                        message = dtoState.message)
                }
            }
    }
}