package com.vpdmoney.vpdmoneypay_kelviniyalo.presentation.main_app.transfer

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.text.Editable
import android.text.TextWatcher
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Adapter
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.vpdmoney.vpdmoneypay_kelviniyalo.R
import com.vpdmoney.vpdmoneypay_kelviniyalo.common.Helper
import com.vpdmoney.vpdmoneypay_kelviniyalo.common.Helper.hideKeyboard
import com.vpdmoney.vpdmoneypay_kelviniyalo.common.Helper.showMessage
import com.vpdmoney.vpdmoneypay_kelviniyalo.common.UiState
import com.vpdmoney.vpdmoneypay_kelviniyalo.common.showSuccessDialog
import com.vpdmoney.vpdmoneypay_kelviniyalo.data.model.TransferDetails
import com.vpdmoney.vpdmoneypay_kelviniyalo.databinding.FragmentFundTransferBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FundTransferFragment : Fragment(R.layout.fragment_fund_transfer) {

    private lateinit var binding: FragmentFundTransferBinding
    private val viewModel: TransferViewModel by viewModels()
    private var selectedAccountType = ""
    private var selectedBank = ""
    lateinit var dialog: AlertDialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFundTransferBinding.bind(view)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        manageTransitionAnimation()
        accountBalanceObserver()
        selectBeneficiaryBank()

        with(binding) {
            pinEt.addTextChangedListener {
                if (it?.length?.equals(4) == true) {
                    requireActivity().hideKeyboard(
                        this@FundTransferFragment.requireView()
                    )
                }
                validateTransactionDetails()
            }
            beneficiaryAccountEt.addTextChangedListener(textWatcher)
            amountEt.addTextChangedListener(textWatcher)
            proceedBtn.setOnClickListener {
                animateAndNavigation()
            }
        }
    }

    private fun animateAndNavigation() {
        val animationBounce = AnimationUtils.loadAnimation(requireContext(), R.anim.btn_bounce)
        binding.proceedBtn.startAnimation(animationBounce)
        val transactionDetails = TransferDetails(
            accountType = selectedAccountType,
            amount = binding.amountEt.text.toString().toDouble(),
            beneficiaryBank = selectedBank,
            beneficiaryAccount = binding.beneficiaryAccountEt.text.toString(),
            sourceBalance = binding.accountBalance.text.toString(),
            transactionTime = Helper.getCurrentDateTimeFormatted(),
            isCredit = false,
            status = "Successful"
        )
        Handler().postDelayed({
            viewModel.handleFundTransfer(transactionDetails)
                .observe(viewLifecycleOwner, Observer { response ->
                    handleUiState(response)
                })
        }, 400)

    }

    private fun accountBalanceObserver() {

        viewModel.getAccountTypes().observe(viewLifecycleOwner, Observer { response ->
            when (response) {

                is UiState.Loading -> {

                }

                is UiState.Success -> {

                    with(binding.dropdownMenu) {
                        setAdapter(setDropDownAdapter(response.data))
                        setOnItemClickListener { parent, view, position, _ ->
                            selectedAccountType = parent.getItemAtPosition(position).toString()
                            validateTransactionDetails()
                            validateAccountDetails(position)
                        }
                    }
                }

                is UiState.DisplayError -> {

                }
            }
        })
    }

    private fun selectBeneficiaryBank() {
        viewModel.getListOfBank().observe(viewLifecycleOwner, Observer { response ->
            when (response) {

                is UiState.Loading -> {

                }

                is UiState.Success -> {

                    with(binding.dropdownBank) {
                        setAdapter(setDropDownAdapter(response.data))

                        setOnItemClickListener { parent, view, position, _ ->
                            selectedBank = parent.getItemAtPosition(position).toString()
                            validateTransactionDetails()
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
            val isSelectedFieldValid = selectedAccountType.isNotEmpty() && selectedBank.isNotEmpty()
            val isTextFieldValid =
                amountEt.text.isNotEmpty() && beneficiaryAccountEt.text.isNotEmpty() && pinEt.text.trim().length.equals(
                    4
                )
            proceedBtn.isEnabled = isSelectedFieldValid && isTextFieldValid
        }
        handleErrorState(false)
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            validateTransactionDetails()
        }

        override fun afterTextChanged(s: Editable?) {
            // Do nothing after text is changed
        }
    }

    private fun manageTransitionAnimation() {
        val animation =
            TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
        sharedElementEnterTransition = animation
        sharedElementReturnTransition = animation
    }

    private fun handleUiState(response: UiState<TransferDetails>) {
        when (response) {
            is UiState.Loading -> {
                dialog = Helper.showLoadingDialog(requireActivity())
            }

            is UiState.Success -> {
                dialog.dismiss()
                requireActivity().showSuccessDialog(
                    response.data,
                    onContinueBtn = {
                        val direction =
                            FundTransferFragmentDirections.actionFundTransferFragmentToTransactionDetailsFragment(
                                response.data
                            )
                        val extras = FragmentNavigatorExtras(
                            binding.proceedBtn to "transfer_btn",
                            binding.toolbar to "toolbar_receipt"
                        )
                        findNavController().navigate(direction, navigatorExtras = extras)
                        // Handle continue button click
                    }
                )
            }

            is UiState.DisplayError -> {
                dialog.dismiss()
                binding.root.showMessage(response.error)
                handleErrorState(true, response.error)
            }
        }
    }

    private fun handleErrorState(isError: Boolean, message: String? = null) {

        with(binding) {
            sourceAccountTv.setTextColor(resources.getColor(if (isError) R.color.red else R.color.blue))
            accountBalance.setTextColor(resources.getColor(if (isError) R.color.red else R.color.blue))
            if (isError) {
                transAmount.error = message
            } else {
                transAmount.isErrorEnabled = false
            }
        }
    }

    private fun setDropDownAdapter(list: List<String>): ArrayAdapter<String> {
        return ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            list
        )
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
}