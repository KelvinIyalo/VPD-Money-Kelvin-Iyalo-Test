package com.vpdmoney.vpdmoneypay_kelviniyalo.presentation.main_app.account

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.vpdmoney.vpdmoneypay_kelviniyalo.R
import com.vpdmoney.vpdmoneypay_kelviniyalo.databinding.FragmentAccountBinding

class AccountFragment : Fragment(R.layout.fragment_account) {

    private lateinit var binding: FragmentAccountBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAccountBinding.bind(view)
    }
}