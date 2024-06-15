package com.vpdmoney.vpdmoneypay_kelviniyalo.di

import android.content.Context
import androidx.room.Room
import com.vpdmoney.vpdmoneypay_kelviniyalo.data.cutomer_account.CustomerAccountRepositoryImpl
import com.vpdmoney.vpdmoneypay_kelviniyalo.data.database.DatabaseDao
import com.vpdmoney.vpdmoneypay_kelviniyalo.data.database.EodDatabase
import com.vpdmoney.vpdmoneypay_kelviniyalo.data.sharedPreference.DataManagerImpl
import com.vpdmoney.vpdmoneypay_kelviniyalo.data.transaction_history.TransactionHistoryRepositoryImpl
import com.vpdmoney.vpdmoneypay_kelviniyalo.data.user_auth.UserAuthRepositoryImpl
import com.vpdmoney.vpdmoneypay_kelviniyalo.domain.cutomer_account.CustomerAccountRepository
import com.vpdmoney.vpdmoneypay_kelviniyalo.domain.sharedPreference.DataManager
import com.vpdmoney.vpdmoneypay_kelviniyalo.domain.transaction_history.TransactionHistoryRepository
import com.vpdmoney.vpdmoneypay_kelviniyalo.domain.user_auth.UserAuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object VPDAppModule {

    @Provides
    @Singleton
    fun providesContext(@ApplicationContext context: Context) = context

    @Singleton
    @Provides
    fun provideDataManager(context: Context) = DataManagerImpl(context) as DataManager

    @Provides
    @Singleton
    fun provideCustomerAccountRepo(dataManager: DataManager) =
        CustomerAccountRepositoryImpl(dataManager) as CustomerAccountRepository

    @Singleton
    @Provides
    fun ProvidesDataBase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, EodDatabase::class.java, "transaction_entity_db").build()

    @Singleton
    @Provides
    fun provideTransactionEntityDao(database: EodDatabase) = database.databaseDao()
    @Provides
    @Singleton
    fun provideEODRepo(databaseDao: DatabaseDao) =
        TransactionHistoryRepositoryImpl(databaseDao) as TransactionHistoryRepository

    @Provides
    @Singleton
    fun provideUserAuthRepo() =
        UserAuthRepositoryImpl() as UserAuthRepository
}