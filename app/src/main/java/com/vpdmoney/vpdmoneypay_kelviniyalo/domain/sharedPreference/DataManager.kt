package com.vpdmoney.vpdmoneypay_kelviniyalo.domain.sharedPreference

interface DataManager {

    fun saveData(key: String, value: String)

    fun getSavedData(key: String): String?

    fun clearSavedData()
}