package currencyexchange.data.datasource.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 * Entity for exchange rates in Database
 *
 * Created by Tan N. Truong, on 12 April, 2023
 * Email: ludugz@gmail.com
 */
@Entity(tableName = "rate")
data class ExchangeRateEntity(
    @PrimaryKey val shortForm: String,
    @ColumnInfo(name = "time_stamp") val timestamp: Long,
    @ColumnInfo(name = "value") val value: Double,
    @ColumnInfo(name = "base_currency") val baseCurrency: String,
)
