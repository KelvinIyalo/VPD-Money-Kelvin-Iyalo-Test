package com.vpdmoney.vpdmoneypay_kelviniyalo.presentation.main_app.transaction_history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vpdmoney.vpdmoneypay_kelviniyalo.R
import com.vpdmoney.vpdmoneypay_kelviniyalo.databinding.FragmentTransactionHistoryBinding


class TransactionHistoryFragment : Fragment(R.layout.fragment_transaction_history) {
    private lateinit var binding: FragmentTransactionHistoryBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTransactionHistoryBinding.bind(view)
    }
}