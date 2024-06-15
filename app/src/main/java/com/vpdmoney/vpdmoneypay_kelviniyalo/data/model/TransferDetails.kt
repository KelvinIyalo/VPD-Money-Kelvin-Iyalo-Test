package com.vpdmoney.vpdmoneypay_kelviniyalo.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "TransactionDatabase")
data class TransferDetails(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    var accountType: String,
    var amount: Double,
    var beneficiaryBank: String,
    var beneficiaryAccount: String,
    var sourceBalance: String,
    var transactionTime: String,
    var responseMessage: String = "",
    var isCredit:Boolean,
    var status:String,
    var transactionTypeName:String ="Fund Transfer",

):Serializable
