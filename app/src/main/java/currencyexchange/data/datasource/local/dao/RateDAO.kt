package currencyexchange.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import currencyexchange.data.datasource.local.entities.ExchangeRateEntity


/**
 * Created by Tan N. Truong, on 12 April, 2023
 * Email: ludugz@gmail.com
 */
@Dao
interface RateDAO {

    @Query("SELECT * FROM rate")
    fun getAll(): List<ExchangeRateEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(rates: List<ExchangeRateEntity>)

    @Query("SELECT time_stamp FROM rate WHERE shortForm = :shortForm")
    fun getTimestamp(shortForm: String): Long
}