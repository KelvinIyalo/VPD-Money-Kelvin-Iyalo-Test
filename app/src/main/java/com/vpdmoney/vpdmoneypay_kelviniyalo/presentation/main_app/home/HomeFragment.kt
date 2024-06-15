package com.vpdmoney.vpdmoneypay_kelviniyalo.presentation.main_app.home

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.vpdmoney.vpdmoneypay_kelviniyalo.R
import com.vpdmoney.vpdmoneypay_kelviniyalo.adapter.AvailableAccountsAdapter
import com.vpdmoney.vpdmoneypay_kelviniyalo.common.UiState
import com.vpdmoney.vpdmoneypay_kelviniyalo.data.model.UserAccounts
import com.vpdmoney.vpdmoneypay_kelviniyalo.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var availableAccountsAdapter: AvailableAccountsAdapter
    private val viewModel: HomeViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        manageRecyclerViewAdapter()
        accountBalanceObserver()
        binding.transfer.setOnClickListener {
            animateAndNavigation()
        }
    }

    private fun manageRecyclerViewAdapter() {
        availableAccountsAdapter = AvailableAccountsAdapter(
            context = requireContext(),
            onItemClicked = { position: Int, itemAtPosition: UserAccounts ->

            }
        )
        binding.accountsRecyclerview.adapter = availableAccountsAdapter
    }

    private fun animateAndNavigation() {
        val animationBounce = AnimationUtils.loadAnimation(requireContext(), R.anim.btn_bounce)
        binding.transfer.startAnimation(animationBounce)
        Handler().postDelayed({
            val direction = R.id.action_navigation_home_to_fundTransferFragment
            val extras = FragmentNavigatorExtras(
                binding.transfer to "toolbar_transfer"
            )
            findNavController().navigate(direction, null, null, navigatorExtras = extras)
        }, 400)

    }

    private fun accountBalanceObserver() {
        viewModel.getCustomerAccounts().observe(viewLifecycleOwner, Observer { response ->
            when (response) {

                is UiState.Loading -> {

                }

                is UiState.Success -> {
                    availableAccountsAdapter.submitList(response.data)
                }

                is UiState.DisplayError -> {

                }
            }


        })
    }
}