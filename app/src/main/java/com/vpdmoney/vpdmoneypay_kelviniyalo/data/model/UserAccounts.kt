package com.vpdmoney.vpdmoneypay_kelviniyalo.data.model

data class UserAccounts(
    var accountType: String,
    var accountNumber: String,
    var accountBalance: Double,
    var tag: String,
    var formattedAmount:String? = null
)

