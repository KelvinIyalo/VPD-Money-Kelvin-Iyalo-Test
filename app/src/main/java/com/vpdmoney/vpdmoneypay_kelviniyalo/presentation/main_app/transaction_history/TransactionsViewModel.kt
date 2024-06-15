package com.vpdmoney.vpdmoneypay_kelviniyalo.presentation.main_app.transaction_history

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.vpdmoney.vpdmoneypay_kelviniyalo.data.model.TFUiState
import com.vpdmoney.vpdmoneypay_kelviniyalo.data.model.TransferDetails
import com.vpdmoney.vpdmoneypay_kelviniyalo.domain.transaction_history.TransactionHistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import com.vpdmoney.vpdmoneypay_kelviniyalo.data.model.Transactions
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class TransactionsViewModel @Inject constructor(private val transactionsRepository: TransactionHistoryRepository) :
    ViewModel() {


    private val _searchQuery = MutableStateFlow("")


    val filteredRecords = MutableLiveData<Transactions>(Transactions.ALL)

    fun getAllSavedFromDb(transactions: Transactions,): LiveData<List<TransferDetails>> {
        return transactionsRepository.getTransactionsFromDb().map { records ->
            when (transactions) {
                Transactions.ALL -> records.filter {
                    searchFilterPredicate(_searchQuery.value, it)
                }
                Transactions.CREDIT -> records.filter { it.isCredit }.filter {
                    searchFilterPredicate(_searchQuery.value, it)
                }
                Transactions.DEBIT -> records.filter { !it.isCredit }.filter {
                    searchFilterPredicate(_searchQuery.value, it)
                }
            }
        }
    }

    fun filterByAll() {
        filteredRecords.value = Transactions.ALL
    }

    fun filterByCredit() {
        filteredRecords.value = Transactions.CREDIT
    }

    fun filterByDebit() {
        filteredRecords.value = Transactions.DEBIT

    }

    fun filterBySearchQuery(searchQuery: String) {
        _searchQuery.value = searchQuery
        Log.d("XXXX filterBySearchQuery", _searchQuery.value)
    }

    private fun searchFilterPredicate(searchQuery: String, transaction: TransferDetails): Boolean {
        return transaction.transactionTypeName?.contains(searchQuery, true) == true ||
                transaction.transactionTime.contains(searchQuery, true) ||
                transaction.amount.toString().contains(searchQuery, true) ||
                transaction.status?.contains(searchQuery, true) == true
    }

}