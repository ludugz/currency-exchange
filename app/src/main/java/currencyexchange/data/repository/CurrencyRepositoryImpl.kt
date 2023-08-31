package currencyexchange.data.repository

import android.util.Log
import androidx.annotation.VisibleForTesting
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import currencyexchange.common.Constants.CURRENCY_LOCAL_DATA_SOURCE
import currencyexchange.common.Constants.CURRENCY_REMOTE_DATA_SOURCE
import currencyexchange.common.Constants.DEFAULT_CURRENCY_BASE_CURRENCY
import currencyexchange.common.Constants.EXPIRED_DURATION
import currencyexchange.common.Constants.UNDEFINED_TIME_STAMP
import currencyexchange.common.NetworkState
import currencyexchange.data.datasource.CurrencyDataSource
import currencyexchange.data.dto.CurrenciesDTO
import currencyexchange.data.dto.ExchangeRatesDTO
import retrofit2.HttpException
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Named


/**
 * Created by Tan N. Truong, on 12 April, 2023
 * Email: ludugz@gmail.com
 */
class CurrencyRepositoryImpl @Inject constructor(
    @Named(CURRENCY_LOCAL_DATA_SOURCE) private var localDataSource: CurrencyDataSource,
    @Named(CURRENCY_REMOTE_DATA_SOURCE) private var remoteDataSource: CurrencyDataSource,
) : CurrencyRepository {

    private val nowInMillis
        get() : Long {
            return System.currentTimeMillis()
        }

    private var currencies: CurrenciesDTO = CurrenciesDTO.DEFAULT

    private var rates: ExchangeRatesDTO = ExchangeRatesDTO.DEFAULT

    override fun getCurrencies(): Flow<NetworkState<CurrenciesDTO>> = flow {
        try {
            emit(NetworkState.Loading())
            if (isCurrencyExistInDB()) {
                getCurrenciesFromDB()
            } else {
                getCurrenciesRemotely()
                updateCurrenciesToDB()
            }
            emit(NetworkState.Success(resource = currencies))
        } catch (exception: HttpException) {
            Log.e(TAG, "Couldn't reach server ${exception.message}")
            emit(NetworkState.Failure(exception = exception, message = "Couldn't reach server"))
        } catch (exception: Exception) {
            Log.e(TAG, "Failed to getCurrencies $exception")
            emit(
                NetworkState.Failure(exception = exception,
                message = "Unknown Error. Please try again!"))
        }
    }

    override fun getExchangeRates(appId: String): Flow<NetworkState<ExchangeRatesDTO>> =
        flow {
            try {
                emit(NetworkState.Loading())
                if (!isRateExistInDB() || isCachedExpired()) {
                    getRatesRemotely(appId = appId)
                    updateExchangeRatesToDB(rates = rates)
                } else {
                    getLatestRateFromDB(appId = appId)
                }
                emit(NetworkState.Success(rates))
            } catch (exception: HttpException) {
                Log.e(TAG, "Couldn't reach server ${exception.message}")
                emit(NetworkState.Failure(exception = exception, message = "Couldn't reach Server"))
            } catch (exception: Exception) {
                Log.e(TAG, "Failed to get latest rate $exception")
                emit(
                    NetworkState.Failure(exception = exception,
                    message = "Unknown Error. Please try again!"))
            }
        }

    private suspend fun isCachedExpired(): Boolean {
        val timestamp =
            localDataSource.getLatestExchangeRateTimestamp(shortForm = DEFAULT_CURRENCY_BASE_CURRENCY)
        if (timestamp == UNDEFINED_TIME_STAMP) return false
        val minutesLeft =
            EXPIRED_DURATION - TimeUnit.MILLISECONDS.toMinutes(nowInMillis - timestamp)
        if (minutesLeft < 0) {
            Log.d(
                TAG,
                "Starting to fetch data remotely...")
        } else {
            Log.d(
                TAG,
                "$minutesLeft minutes left before fetching data remotely...")
        }
        return (nowInMillis - timestamp) > TimeUnit.MINUTES.toMillis(EXPIRED_DURATION)
    }

    //region currencies handling
    @VisibleForTesting
    internal suspend fun getCurrenciesRemotely() {
        Log.d(TAG, "Fetch currencies remotely")
        currencies = remoteDataSource.getCurrencies()
    }

    @VisibleForTesting
    internal suspend fun getCurrenciesFromDB() {
        Log.d(TAG, "Fetch currencies from DB")
        currencies = localDataSource.getCurrencies()
    }

    @VisibleForTesting
    internal suspend fun updateCurrenciesToDB() {
        Log.d(TAG, "Update currencies to DB")
        localDataSource.updateCurrencies(currencies = currencies)
    }

    @VisibleForTesting
    internal suspend fun isCurrencyExistInDB(): Boolean {
        return localDataSource.isCurrenciesExistInDB()
    }
    //endregion

    //region rates handling
    @VisibleForTesting
    internal suspend fun getRatesRemotely(appId: String) {
        Log.d(TAG, "Fetch exchange rates remotely")
        rates = remoteDataSource.getExchangeRates(appId = appId).apply {
            timestamp = nowInMillis
        }
    }

    @VisibleForTesting
    internal suspend fun getLatestRateFromDB(appId: String) {
        Log.d(TAG, "Fetch exchange rates from DB")
        rates = localDataSource.getExchangeRates(appId = appId)
    }

    @VisibleForTesting
    internal suspend fun updateExchangeRatesToDB(rates: ExchangeRatesDTO) {
        Log.d(TAG, "Update exchange rates to DB")
        localDataSource.updateExchangeRates(rates = rates)
    }

    @VisibleForTesting
    internal suspend fun isRateExistInDB(): Boolean {
        return localDataSource.isExchangeRatesExistInDB()
    }
    //endregion

    companion object {
        const val TAG = "CurrencyRepositoryImpl"
    }
}