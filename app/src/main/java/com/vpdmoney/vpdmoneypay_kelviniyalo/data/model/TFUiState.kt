package com.vpdmoney.vpdmoneypay_kelviniyalo.data.model

import androidx.lifecycle.LiveData

data class TFUiState(
    val transactionType: Transactions,
    val transactions: LiveData<List<TransferDetails>>?
)