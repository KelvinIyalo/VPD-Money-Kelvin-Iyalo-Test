package com.vpdmoney.vpdmoneypay_kelviniyalo.presentation.auth.signin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vpdmoney.vpdmoneypay_kelviniyalo.common.UiState
import com.vpdmoney.vpdmoneypay_kelviniyalo.data.model.AuthResponseHandler
import com.vpdmoney.vpdmoneypay_kelviniyalo.domain.user_auth.UserAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserLoginViewModel @Inject constructor(
    private val authRepository: UserAuthRepository
) :
    ViewModel() {

    val transactionListener = MutableLiveData<UiState<AuthResponseHandler>>()
    fun userLogin(email: String, password: String) {
        viewModelScope.launch {
            transactionListener.value = UiState.Loading
            val result = authRepository.userLogin(email, password)
            result.addOnCompleteListener {
                if (it.isSuccessful) {
                    transactionListener.value =
                        UiState.Success(AuthResponseHandler(it.isSuccessful))
                }
            }
                .addOnFailureListener {
                    if (it.message?.isNotEmpty() == true) {
                        transactionListener.value = UiState.DisplayError(it.message.toString())
                    }
                }
        }

    }

}