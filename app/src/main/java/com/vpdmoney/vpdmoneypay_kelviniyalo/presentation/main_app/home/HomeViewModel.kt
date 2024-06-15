package com.vpdmoney.vpdmoneypay_kelviniyalo.presentation.main_app.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.vpdmoney.vpdmoneypay_kelviniyalo.common.UiState
import com.vpdmoney.vpdmoneypay_kelviniyalo.domain.cutomer_account.CustomerAccountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val customerAccountRepository: CustomerAccountRepository) :
    ViewModel() {

    fun getCustomerAccounts() = liveData {
        emit(UiState.Loading)
        val customerAccount = customerAccountRepository.getCustomerAccount()
        emit(UiState.Success(customerAccount))
    }

}