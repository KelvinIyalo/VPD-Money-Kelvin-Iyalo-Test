package com.vpdmoney.vpdmoneypay_kelviniyalo.presentation.main_app.account

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.vpdmoney.vpdmoneypay_kelviniyalo.R
import com.vpdmoney.vpdmoneypay_kelviniyalo.common.Helper
import com.vpdmoney.vpdmoneypay_kelviniyalo.common.Helper.hideKeyboard
import com.vpdmoney.vpdmoneypay_kelviniyalo.common.Helper.showMessage
import com.vpdmoney.vpdmoneypay_kelviniyalo.common.UiState
import com.vpdmoney.vpdmoneypay_kelviniyalo.common.showSuccessDialog
import com.vpdmoney.vpdmoneypay_kelviniyalo.data.model.TransferDetails
import com.vpdmoney.vpdmoneypay_kelviniyalo.databinding.FragmentAccountBinding
import com.vpdmoney.vpdmoneypay_kelviniyalo.presentation.main_app.transfer.TransferViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountFragment : Fragment(R.layout.fragment_account) {

    private lateinit var binding: FragmentAccountBinding
    private val viewModel: TransferViewModel by viewModels()
    private var selectedAccountType = ""
    private var selectedPosition: Int = 0
    lateinit var dialog: AlertDialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAccountBinding.bind(view)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        selectAccountType()

        with(binding) {
            amountFundEt.addTextChangedListener {
                validateTransactionDetails()
            }

            proceedFundBtn.setOnClickListener {
                requireActivity().hideKeyboard(
                    this@AccountFragment.requireView()
                )
                animateAndNavigation()
            }
        }

    }


    private fun animateAndNavigation() {
        val animationBounce = AnimationUtils.loadAnimation(requireContext(), R.anim.btn_bounce)
        binding.proceedFundBtn.startAnimation(animationBounce)

        Handler().postDelayed({
            val transactionDetails = TransferDetails(
                accountType = selectedAccountType,
                amount = binding.amountFundEt.text.toString().toDouble(),
                beneficiaryBank = "",
                beneficiaryAccount = "",
                sourceBalance = binding.accountBalance.text.toString(),
                transactionTime = Helper.getCurrentDateTimeFormatted(),
                responseMessage = "",
                isCredit = true,
                status = "Successful"
            )
            viewModel.fundWallet(binding.amountFundEt.text.toString().toDouble(),transactionDetails)
                .observe(viewLifecycleOwner, Observer { response ->
                    handleUiState(response)
                })
        }, 400)

    }

    private fun handleUiState(response:  UiState<TransferDetails>) {
        when (response) {
            is UiState.Loading -> {
                dialog = Helper.showLoadingDialog(requireActivity())
            }

            is UiState.Success -> {
                dialog.dismiss()

                requireActivity().showSuccessDialog(
                    response.data,
                    onContinueBtn = {
                        validateAccountDetails(selectedPosition)
                    }
                )
            }

            is UiState.DisplayError -> {
                dialog.dismiss()
                binding.root.showMessage(response.error)
            }
        }
    }

    private fun validateAccountDetails(position: Int) {
        viewModel.getSourceAccountBalance(position)
            .observe(viewLifecycleOwner, Observer { response ->
                when (response) {

                    is UiState.Loading -> {

                    }

                    is UiState.Success -> {
                        binding.accountBalance.text = response.data.formattedAmount
                        binding.sourceAccountTv.text = response.data.accountNumber
                    }

                    is UiState.DisplayError -> {

                    }
                }

            })
    }

    private fun selectAccountType() {
        viewModel.getAccountTypes().observe(viewLifecycleOwner, Observer { response ->
            when (response) {

                is UiState.Loading -> {

                }

                is UiState.Success -> {

                    with(binding.dropdownMenu) {
                        val adapter = ArrayAdapter(
                            requireContext(),
                            android.R.layout.simple_dropdown_item_1line,
                            response.data
                        )
                        setAdapter(adapter)

                        setOnItemClickListener { parent, view, position, _ ->
                            selectedAccountType = parent.getItemAtPosition(position).toString()
                            validateTransactionDetails()
                            selectedPosition = position
                            validateAccountDetails(selectedPosition)

                        }
                    }
                }

                is UiState.DisplayError -> {

                }
            }
        })
    }

    private fun validateTransactionDetails() {
        with(binding) {
            val isSelectedFieldValid =
                selectedAccountType.isNotEmpty() && amountFundEt.text.isNotEmpty()
            proceedFundBtn.isEnabled = isSelectedFieldValid
        }
    }
}