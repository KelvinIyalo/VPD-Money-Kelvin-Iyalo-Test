package com.vpdmoney.vpdmoneypay_kelviniyalo.domain.transaction_history

import androidx.lifecycle.LiveData
import com.vpdmoney.vpdmoneypay_kelviniyalo.data.model.TransferDetails

interface TransactionHistoryRepository {

    suspend fun saveToDb(entity: TransferDetails): Long

    fun getTransactionsFromDb(): LiveData<List<TransferDetails>>

    suspend fun deleteAllFromDb()
}