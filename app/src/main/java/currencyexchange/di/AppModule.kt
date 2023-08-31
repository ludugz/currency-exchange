package currencyexchange.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import currencyexchange.common.Constants.BASE_CURRENCY_URL
import currencyexchange.common.Constants.CURRENCY_LOCAL_DATA_SOURCE
import currencyexchange.common.Constants.CURRENCY_REMOTE_DATA_SOURCE
import currencyexchange.data.datasource.CurrencyDataSource
import currencyexchange.data.datasource.local.CurrencyLocalDataSourceImpl
import currencyexchange.data.datasource.local.dao.CurrencyDAO
import currencyexchange.data.datasource.local.dao.RateDAO
import currencyexchange.data.datasource.local.database.AppDatabase
import currencyexchange.data.datasource.remote.ApiClient
import currencyexchange.data.datasource.remote.CurrencyRemoteDataSourceImpl
import currencyexchange.data.repository.CurrencyRepository
import currencyexchange.data.repository.CurrencyRepositoryImpl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton


/**
 * Created by Tan N. Truong, on 12 April, 2023
 * Email: ludugz@gmail.com
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApi(): ApiClient {
        val client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
        return Retrofit.Builder()
            .baseUrl(BASE_CURRENCY_URL).client(client)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(ApiClient::class.java)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideCurrencyDao(appDatabase: AppDatabase): CurrencyDAO {
        return appDatabase.currencyDao()
    }


    @Provides
    @Singleton
    fun provideExchangeRateDao(appDatabase: AppDatabase): RateDAO {
        return appDatabase.rateDao()
    }

    @Provides
    @Named(CURRENCY_REMOTE_DATA_SOURCE)
    @Singleton
    fun provideCurrencyRemoteDataSource(apiClient: ApiClient): CurrencyDataSource {
        return CurrencyRemoteDataSourceImpl(apiClient = apiClient)
    }

    @Provides
    @Named(CURRENCY_LOCAL_DATA_SOURCE)
    @Singleton
    fun provideCurrencyLocalDataSource(
        currencyDAO: CurrencyDAO,
        rateDAO: RateDAO,
    ): CurrencyDataSource {
        return CurrencyLocalDataSourceImpl(
            currencyDAO = currencyDAO,
            rateDAO = rateDAO
        )
    }

    @Provides
    @Singleton
    fun provideCurrencyRepository(
        @Named(CURRENCY_LOCAL_DATA_SOURCE)
        localDataSource: CurrencyDataSource,
        @Named(CURRENCY_REMOTE_DATA_SOURCE)
        remoteDataSource: CurrencyDataSource,
    ): CurrencyRepository {
        return CurrencyRepositoryImpl(
            localDataSource = localDataSource,
            remoteDataSource = remoteDataSource
        )
    }
}