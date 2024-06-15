package com.vpdmoney.vpdmoneypay_kelviniyalo.presentation.main_app.transaction_details

import android.os.Bundle
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.vpdmoney.vpdmoneypay_kelviniyalo.R
import com.vpdmoney.vpdmoneypay_kelviniyalo.common.Helper
import com.vpdmoney.vpdmoneypay_kelviniyalo.data.model.TransferDetails
import com.vpdmoney.vpdmoneypay_kelviniyalo.databinding.FragmentTransactionDetailsBinding


class TransactionDetailsFragment : Fragment(R.layout.fragment_transaction_details) {

    private lateinit var binding: FragmentTransactionDetailsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTransactionDetailsBinding.bind(view)
        val fundTransferArgs: TransactionDetailsFragmentArgs by navArgs()
        manageTransitionAnimation()
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack(
                R.id.navigation_home,
                false
            )
        }
        validateUiItems(fundTransferArgs.transferDetails)


        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private fun validateUiItems(transDetails: TransferDetails) {
        with(binding) {
            val balanceSign = transDetails.sourceBalance.take(1)
            amount.text = "$balanceSign${Helper.formatAmount(transDetails.amount)}"
            beneficiaryValue.text =
                "${"Kelvin Iyalo"}\n ${transDetails.beneficiaryAccount}\n ${transDetails.beneficiaryBank}"
            remarkValue.text = getString(R.string.purchase_of_goods)
            dateTime.text = transDetails.transactionTime
            transRefValue.text = "EFT-${transDetails.transactionTime.replace('-', ' ')}"
            dateGenerated.text = "Generated from VPD Money on ${
                transDetails.transactionTime.replace('-', '/')
            }"
        }
    }

    private fun manageTransitionAnimation() {
        val animation = TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
        sharedElementEnterTransition = animation
        sharedElementReturnTransition = animation
    }

    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            findNavController().popBackStack(
                R.id.navigation_home,
                false
            )
        }
    }
}