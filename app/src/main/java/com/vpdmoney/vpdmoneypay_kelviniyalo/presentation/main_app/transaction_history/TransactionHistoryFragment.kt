package com.vpdmoney.vpdmoneypay_kelviniyalo.presentation.main_app.transaction_history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.vpdmoney.vpdmoneypay_kelviniyalo.R
import com.vpdmoney.vpdmoneypay_kelviniyalo.adapter.TransactionsAdapter
import com.vpdmoney.vpdmoneypay_kelviniyalo.common.UiState
import com.vpdmoney.vpdmoneypay_kelviniyalo.databinding.FragmentTransactionHistoryBinding
import dagger.hilt.android.AndroidEntryPoint
import com.vpdmoney.vpdmoneypay_kelviniyalo.data.model.Transactions
import com.vpdmoney.vpdmoneypay_kelviniyalo.data.model.TransferDetails
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.ArrayList


@AndroidEntryPoint
class TransactionHistoryFragment : Fragment(R.layout.fragment_transaction_history) {
    private lateinit var binding: FragmentTransactionHistoryBinding
    private val viewModel: TransactionsViewModel by viewModels()
    private lateinit var transactionsAdapter: TransactionsAdapter
    private val searchQuery = MutableStateFlow("")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTransactionHistoryBinding.bind(view)

        initTransactionsRecyclerViewAdapter()

        initClickListeners()

        viewModel.filteredRecords.observe(viewLifecycleOwner, Observer {
            viewModel.getAllSavedFromDb(it)
                .observe(viewLifecycleOwner, Observer {
                    transactionsAdapter.submitList(it)
                })
            updateSelectedTab(it)
        })

        binding.etSearchText.addTextChangedListener {
            viewModel.filterBySearchQuery(it.toString())
        }

    }

    private fun initClickListeners() {
        binding.transactionsMaterialToolbar.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.allTV.setOnClickListener {
            viewModel.filterByAll()
        }

        binding.creditTV.setOnClickListener {
            viewModel.filterByCredit()
        }

        binding.debitTV.setOnClickListener {
            viewModel.filterByDebit()
        }
    }

    private fun initTransactionsRecyclerViewAdapter() {
        transactionsAdapter = TransactionsAdapter(
            requireContext(),
            onItemClicked = { position: Int, itemAtPosition: TransferDetails ->

            }
        )
        binding.transactionsRV.adapter = transactionsAdapter
    }

    private fun updateSelectedTab(transactionType: Transactions) {
        when (transactionType) {
            Transactions.ALL -> {
                binding.allLiner.setBackgroundResource(R.color.blue)
                binding.creditLiner.setBackgroundResource(R.color.white)
                binding.debitLiner.setBackgroundResource(R.color.white)
            }

            Transactions.CREDIT -> {
                binding.creditLiner.setBackgroundResource(R.color.text_bg_end)
                binding.allLiner.setBackgroundResource(R.color.white)
                binding.debitLiner.setBackgroundResource(R.color.white)
            }

            Transactions.DEBIT -> {
                binding.debitLiner.setBackgroundResource(R.color.blue)
                binding.allLiner.setBackgroundResource(R.color.white)
                binding.creditLiner.setBackgroundResource(R.color.white)
            }
        }
    }
}