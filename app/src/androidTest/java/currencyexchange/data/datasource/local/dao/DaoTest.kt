package currencyexchange.data.datasource.local.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import currencyexchange.data.datasource.local.dao.CurrencyDAO
import currencyexchange.data.datasource.local.dao.RateDAO
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import currencyexchange.data.datasource.local.database.AppDatabase
import currencyexchange.data.datasource.local.entities.CurrencyEntity
import currencyexchange.data.datasource.local.entities.ExchangeRateEntity
import java.io.IOException

/**
 * Created by Tan N. Truong, on 12 April, 2023
 * Email: ludugz@gmail.com
 */
@RunWith(AndroidJUnit4::class)
class DaoTest {
    private lateinit var database: AppDatabase

    private lateinit var currencyDao: CurrencyDAO
    private lateinit var databaseCurrencies: List<CurrencyEntity>

    private lateinit var rateDao: RateDAO
    private lateinit var databaseRate: List<ExchangeRateEntity>

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java).build()
        currencyDao = database.currencyDao()
        rateDao = database.rateDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDatabase() {
        database.close()
    }

    @Test
    @Throws(Exception::class)
    fun currencyDao() {
        // Validate there is no exist data in Database initially
        databaseCurrencies = currencyDao.getAll()
        assertTrue(databaseCurrencies.isEmpty())

        // Validate insert and get currencies
        val expectedCurrencies = listOf(
            CurrencyEntity("USD", "Dummy Long Form"),
            CurrencyEntity("JPY", "Dummy Long Form"),
        )
        currencyDao.insertAll(expectedCurrencies)
        databaseCurrencies = currencyDao.getAll()

        // Validate if there are any currencies exist
        assertTrue(databaseCurrencies.isNotEmpty())
        assertEquals(expectedCurrencies, databaseCurrencies)
    }

    @Test
    @Throws(Exception::class)
    fun rateDao() {
        // Validate there is no exist data in Database initially
        databaseRate = rateDao.getAll()
        assertTrue(databaseRate.isEmpty())

        // Validate insert and get rates
        val expectedRates = listOf(
            ExchangeRateEntity(shortForm = "USD",
                timestamp = 1L,
                value = 1.0,
                baseCurrency = "USD"),
            ExchangeRateEntity(shortForm = "JPY",
                timestamp = 1L,
                value = 1.0,
                baseCurrency = "USD"),
        )
        rateDao.insertAll(expectedRates)
        databaseRate = rateDao.getAll()

        // Validate if there are any rates exist
        assertTrue(databaseRate.isNotEmpty())
        assertEquals(expectedRates, databaseRate)
    }
}