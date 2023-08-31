package currencyexchange.data.dto

/**
 * Data Transfer Object for currencies when receiving response from API
 *
 * Created by Tan N. Truong, on 12 April, 2023
 * Email: ludugz@gmail.com
 */
data class CurrenciesDTO(
    val currencies: Map<String, String>? = null,
) {

    companion object {
        val DEFAULT = CurrenciesDTO(emptyMap())
    }
}
