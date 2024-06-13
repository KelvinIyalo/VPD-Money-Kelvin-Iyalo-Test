package com.vpdmoney.vpdmoneypay_kelviniyalo.presentation.auth.registration

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.vpdmoney.vpdmoneypay_kelviniyalo.R
import com.vpdmoney.vpdmoneypay_kelviniyalo.databinding.FragmentRegistrationBinding


class RegistrationFragment : Fragment(R.layout.fragment_registration) {
    private lateinit var binding: FragmentRegistrationBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegistrationBinding.bind(view)
    }
}