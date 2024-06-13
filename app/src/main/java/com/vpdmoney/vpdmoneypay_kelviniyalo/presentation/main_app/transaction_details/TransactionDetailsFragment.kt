package com.vpdmoney.vpdmoneypay_kelviniyalo.presentation.main_app.transaction_details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vpdmoney.vpdmoneypay_kelviniyalo.R
import com.vpdmoney.vpdmoneypay_kelviniyalo.databinding.FragmentTransactionDetailsBinding


class TransactionDetailsFragment : Fragment(R.layout.fragment_transaction_details) {

    private lateinit var binding: FragmentTransactionDetailsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTransactionDetailsBinding.bind(view)
    }

}