package com.vpdmoney.vpdmoneypay_kelviniyalo.data.sharedPreference

import android.content.Context
import com.vpdmoney.vpdmoneypay_kelviniyalo.domain.sharedPreference.DataManager
import javax.inject.Inject

class DataManagerImpl @Inject constructor(context: Context) : DataManager {

    private val sharedPreference = context.getSharedPreferences("vpdpref", Context.MODE_PRIVATE)

    override fun saveData(key: String, value: String) {
        sharedPreference.edit().putString(key, value).apply()
    }

    override fun getSavedData(key: String): String? {
        return sharedPreference.getString(key, null)
    }

    override fun clearSavedData() {
        sharedPreference.edit().clear().apply()
    }
}