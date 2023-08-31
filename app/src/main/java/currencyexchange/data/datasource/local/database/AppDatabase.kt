package currencyexchange.data.datasource.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import currencyexchange.data.datasource.local.dao.CurrencyDAO
import currencyexchange.data.datasource.local.dao.RateDAO
import currencyexchange.data.datasource.local.entities.CurrencyEntity
import currencyexchange.data.datasource.local.entities.ExchangeRateEntity


/**
 * Created by Tan N. Truong, on 12 April, 2023
 * Email: ludugz@gmail.com
 */
@Database(entities = [CurrencyEntity::class, ExchangeRateEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun currencyDao(): CurrencyDAO
    abstract fun rateDao(): RateDAO

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java, "currency.db"
            ).build()
        }
    }
}