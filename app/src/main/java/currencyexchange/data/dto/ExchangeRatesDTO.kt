package currencyexchange.data.dto

import com.google.gson.annotations.SerializedName
import currencyexchange.common.Constants.EMPTY_STRING
import currencyexchange.common.Constants.UNDEFINED_TIME_STAMP

/**
 * Data Transfer Object for exchange rates when receiving response from API
 *
 * Created by Tan N. Truong, on 12 April, 2023
 * Email: ludugz@gmail.com
 */
data class ExchangeRatesDTO(
    val disclaimer: String? = null,
    val license: String? = null,
    var timestamp: Long = UNDEFINED_TIME_STAMP,
    @SerializedName("base")
    val baseCurrency: String? = null,
    val rates: Map<String, Double>? = null,
) {

    companion object {
        val DEFAULT = ExchangeRatesDTO(
            disclaimer = EMPTY_STRING,
            license = EMPTY_STRING,
            timestamp = UNDEFINED_TIME_STAMP,
            baseCurrency = EMPTY_STRING,
            rates = emptyMap()
        )
    }
}
