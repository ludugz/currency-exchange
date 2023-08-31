package currencyexchange.domain.bo

/**
 * Currencies Business Object to display in UI
 *
 * Created by Tan N. Truong, on 12 April, 2023
 * Email: ludugz@gmail.com
 */
data class CurrenciesBO(
    val currencies: Map<String, String>
) {

    companion object {
        val DEFAULT = CurrenciesBO(currencies = emptyMap())
    }
}
