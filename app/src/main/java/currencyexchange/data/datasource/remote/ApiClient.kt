package currencyexchange.data.datasource.remote

import currencyexchange.data.dto.ExchangeRatesDTO
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * Created by Tan N. Truong, on 12 April, 2023
 * Email: ludugz@gmail.com
 */
interface ApiClient {

    @GET("/currencies.json")
    suspend fun getCurrencies(): Map<String, String>

    @GET("/latest.json")
    suspend fun getExchangeRates(@Query("app_id") appId: String): ExchangeRatesDTO
}