package com.vpdmoney.vpdmoneypay_kelviniyalo.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vpdmoney.vpdmoneypay_kelviniyalo.data.model.TransferDetails

@Database(entities = [TransferDetails::class], version = 1)
abstract class EodDatabase : RoomDatabase() {
    abstract fun databaseDao(): DatabaseDao

}