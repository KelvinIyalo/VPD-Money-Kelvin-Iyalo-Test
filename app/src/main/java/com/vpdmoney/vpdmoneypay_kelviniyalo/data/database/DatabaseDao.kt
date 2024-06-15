package com.vpdmoney.vpdmoneypay_kelviniyalo.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.vpdmoney.vpdmoneypay_kelviniyalo.data.model.TransferDetails

@Dao
interface DatabaseDao {

    @Insert
    suspend fun insert(entity: TransferDetails): Long

    @Query("DELETE FROM transactiondatabase")
    suspend fun deleteAll()

    @Query(
        """
        SELECT * FROM transactiondatabase
        ORDER BY id DESC
        """
    )
    fun allTransactions( ): LiveData<List<TransferDetails>>
}