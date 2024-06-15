package com.vpdmoney.vpdmoneypay_kelviniyalo.common

sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()

    data class DisplayError(val error: String) : UiState<Nothing>()
}