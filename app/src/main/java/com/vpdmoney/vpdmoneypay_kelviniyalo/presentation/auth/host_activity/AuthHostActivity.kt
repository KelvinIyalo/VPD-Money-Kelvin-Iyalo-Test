package com.vpdmoney.vpdmoneypay_kelviniyalo.presentation.auth.host_activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vpdmoney.vpdmoneypay_kelviniyalo.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthHostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
    }
}