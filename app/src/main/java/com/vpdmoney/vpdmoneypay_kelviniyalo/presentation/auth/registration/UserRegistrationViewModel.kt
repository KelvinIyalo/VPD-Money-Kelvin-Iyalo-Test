package com.vpdmoney.vpdmoneypay_kelviniyalo.presentation.auth.registration

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vpdmoney.vpdmoneypay_kelviniyalo.common.Constants
import com.vpdmoney.vpdmoneypay_kelviniyalo.common.Helper
import com.vpdmoney.vpdmoneypay_kelviniyalo.common.UiState
import com.vpdmoney.vpdmoneypay_kelviniyalo.data.model.AuthResponseHandler
import com.vpdmoney.vpdmoneypay_kelviniyalo.data.model.TransferDetails
import com.vpdmoney.vpdmoneypay_kelviniyalo.domain.sharedPreference.DataManager
import com.vpdmoney.vpdmoneypay_kelviniyalo.domain.transaction_history.TransactionHistoryRepository
import com.vpdmoney.vpdmoneypay_kelviniyalo.domain.user_auth.UserAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserRegistrationViewModel @Inject constructor(
    val authRepository: UserAuthRepository,
    val dataManager: DataManager,
    private val transactionHistoryRepository: TransactionHistoryRepository
) :
    ViewModel() {

    val transactionListener = MutableLiveData<UiState<AuthResponseHandler>>()
    fun registerUser(email: String, password: String) {
        viewModelScope.launch {
            transactionListener.value = UiState.Loading

            val result = authRepository.userRegister(email, password)
            result.addOnCompleteListener {
                if (it.isSuccessful) {
                    transactionListener.value =
                        UiState.Success(AuthResponseHandler(it.isSuccessful))
                    bonusTopUp()
                }
            }
                .addOnFailureListener {
                    if (it.message?.isNotEmpty() == true) {
                        transactionListener.value = UiState.DisplayError(it.message.toString())
                    }
                }
        }


    }

    private fun bonusTopUp() {
        val bonusAmount = "2000"
        val accounts = listOf(
            Constants.SAVINGS_AMOUNT,
            Constants.CURRENT_AMOUNT,
            Constants.DOLLAR_AMOUNT
        )
        viewModelScope.launch {
            accounts.forEach { accountType ->
                dataManager.saveData(accountType, bonusAmount)
                transactionHistoryRepository.saveToDb(
                    getTransactionModel(
                        accountType,
                        bonusAmount.toDouble()
                    )
                )
            }
        }

    }

    private fun getTransactionModel(accountType: String, amount: Double): TransferDetails {
        return TransferDetails(
            accountType = accountType,
            amount = amount,
            beneficiaryBank = "",
            beneficiaryAccount = "",
            sourceBalance = "",
            transactionTime = Helper.getCurrentDateTimeFormatted(),
            responseMessage = "",
            isCredit = true,
            status = "Successful",
            transactionTypeName = "Singup Bonus"
        )
    }
}