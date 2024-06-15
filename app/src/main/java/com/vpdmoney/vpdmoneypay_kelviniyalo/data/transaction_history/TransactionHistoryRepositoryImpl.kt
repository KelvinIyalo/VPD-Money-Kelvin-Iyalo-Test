package com.vpdmoney.vpdmoneypay_kelviniyalo.data.transaction_history

import androidx.lifecycle.LiveData
import com.vpdmoney.vpdmoneypay_kelviniyalo.data.database.DatabaseDao
import com.vpdmoney.vpdmoneypay_kelviniyalo.data.model.TransferDetails
import com.vpdmoney.vpdmoneypay_kelviniyalo.domain.transaction_history.TransactionHistoryRepository
import javax.inject.Inject

class TransactionHistoryRepositoryImpl @Inject constructor(private val databaseDao: DatabaseDao) :
    TransactionHistoryRepository {

    override suspend fun saveToDb(entity: TransferDetails): Long {
        return databaseDao.insert(entity = entity)
    }

    override fun getTransactionsFromDb(): LiveData<List<TransferDetails>> {
        return databaseDao.allTransactions()
    }

    override suspend fun deleteAllFromDb() {
        return databaseDao.deleteAll()

    }
}