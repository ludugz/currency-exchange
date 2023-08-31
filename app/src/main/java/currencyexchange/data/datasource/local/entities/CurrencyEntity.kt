package currencyexchange.data.datasource.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 * Entity for exchange currency in Database
 *
 * Created by Tan N. Truong, on 12 April, 2023
 * Email: ludugz@gmail.com
 */
@Entity(tableName = "currency")
data class CurrencyEntity(
    @PrimaryKey val shortForm: String,
    @ColumnInfo val longForm: String,
)