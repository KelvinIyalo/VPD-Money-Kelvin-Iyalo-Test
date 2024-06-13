package com.vpdmoney.vpdmoneypay_kelviniyalo.presentation.auth.signin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vpdmoney.vpdmoneypay_kelviniyalo.R
import com.vpdmoney.vpdmoneypay_kelviniyalo.databinding.FragmentSignInBinding


class SignInFragment : Fragment(R.layout.fragment_sign_in) {
   private lateinit var binding: FragmentSignInBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignInBinding.bind(view)
    }
}