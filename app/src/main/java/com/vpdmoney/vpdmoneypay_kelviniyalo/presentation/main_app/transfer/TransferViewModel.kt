package com.vpdmoney.vpdmoneypay_kelviniyalo.presentation.main_app.transfer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.vpdmoney.vpdmoneypay_kelviniyalo.common.Helper
import com.vpdmoney.vpdmoneypay_kelviniyalo.common.UiState
import com.vpdmoney.vpdmoneypay_kelviniyalo.data.model.TransferDetails
import com.vpdmoney.vpdmoneypay_kelviniyalo.data.model.UserAccounts
import com.vpdmoney.vpdmoneypay_kelviniyalo.domain.cutomer_account.CustomerAccountRepository
import com.vpdmoney.vpdmoneypay_kelviniyalo.domain.sharedPreference.DataManager
import com.vpdmoney.vpdmoneypay_kelviniyalo.domain.transaction_history.TransactionHistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TransferViewModel @Inject constructor(
    private val customerAccountRepository: CustomerAccountRepository,
    val dataManager: DataManager,
    private val transactionHistoryRepository: TransactionHistoryRepository
) :
    ViewModel() {

    private lateinit var customerAccount: UserAccounts
    fun getSourceAccountBalance(position: Int) = liveData {
        emit(UiState.Loading)
        customerAccount = customerAccountRepository.getCustomerAccount()[position]
        emit(
            UiState.Success(
                customerAccount.copy(
                    formattedAmount = Helper.formatAccountBalance(
                        customerAccount
                    )
                )
            )
        )

    }

    fun handleFundTransfer(transactionDetails: TransferDetails) = liveData {
        emit(UiState.Loading)
        kotlinx.coroutines.delay(4000)
        if (customerAccount.accountBalance < transactionDetails.amount) {
            val response = transactionDetails.copy(responseMessage = "Insufficient Fund")
            emit(UiState.DisplayError(response.responseMessage))
        } else {
            dataManager.saveData(
                customerAccount.tag,
                (customerAccount.accountBalance - transactionDetails.amount).toString()
            )
            val response = transactionDetails.copy(responseMessage = "Transaction Successful")
            transactionHistoryRepository.saveToDb(response)
            emit(UiState.Success(response))
        }

    }

    fun getListOfBank() = liveData {
        emit(UiState.Loading)
        val banks = customerAccountRepository.getGetListOfBanks()
        if (banks.isNotEmpty()) {
            emit(UiState.Success(banks))
        } else {
            emit(UiState.DisplayError("No Bank Available"))
        }
    }

    fun getAccountTypes() = liveData {
        emit(UiState.Loading)
        val banks = customerAccountRepository.getListOfAccountTypes()
        if (banks.isNotEmpty()) {
            emit(UiState.Success(banks))
        } else {
            emit(UiState.DisplayError("No Bank Available"))
        }
    }

    fun fundWallet(topUpAmount:Double,transactionDetails: TransferDetails) = liveData {
        emit(UiState.Loading)
        kotlinx.coroutines.delay(4000)
        if (customerAccount.accountBalance >= 100000.0) {
            val response =  "Maximum balance reached, please upgrade your account"
            emit(UiState.DisplayError(response))
        } else { dataManager.saveData(customerAccount.tag, (customerAccount.accountBalance + topUpAmount).toString())

            val currency = Helper.formatAccountBalance(customerAccount).take(1)
            val response =  "$currency$topUpAmount Funded Successful to ${customerAccount.accountType}"
            val transData = transactionDetails.copy(transactionTypeName ="Wallet Funding" )
            transactionHistoryRepository.saveToDb(transData)
            emit(UiState.Success(transData.copy(responseMessage =response )))

        }

    }
}