package currencyexchange.common

/**
 * Created by Tan N. Truong, on 12 April, 2023
 * Email: ludugz@gmail.com
 */
object Constants {
    const val EMPTY_STRING = ""

    const val UNDEFINED_TIME_STAMP = 0L

    const val DEFAULT_CURRENCY_AMOUNT = 0.0

    const val DEFAULT_CURRENCY_BASE_CURRENCY = "USD"

    const val BASE_CURRENCY_URL = "https://openexchangerates.org/api/"

    const val EXPIRED_DURATION = 30L // In minute

    // Dagger
    const val CURRENCY_REMOTE_DATA_SOURCE = "CURRENCY_REMOTE_DATA_SOURCE"

    const val CURRENCY_LOCAL_DATA_SOURCE = "CURRENCY_LOCAL_DATA_SOURCE"
}