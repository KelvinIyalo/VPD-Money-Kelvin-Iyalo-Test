package com.vpdmoney.vpdmoneypay_kelviniyalo.presentation.main_app.transfer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vpdmoney.vpdmoneypay_kelviniyalo.R
import com.vpdmoney.vpdmoneypay_kelviniyalo.databinding.FragmentFundTransferBinding


class FundTransferFragment : Fragment(R.layout.fragment_fund_transfer) {

    private lateinit var binding: FragmentFundTransferBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFundTransferBinding.bind(view)

    }



}