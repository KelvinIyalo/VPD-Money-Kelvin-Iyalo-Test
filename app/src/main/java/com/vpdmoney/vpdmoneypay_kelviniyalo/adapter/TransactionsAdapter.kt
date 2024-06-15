package com.vpdmoney.vpdmoneypay_kelviniyalo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vpdmoney.vpdmoneypay_kelviniyalo.R
import com.vpdmoney.vpdmoneypay_kelviniyalo.common.Helper
import com.vpdmoney.vpdmoneypay_kelviniyalo.data.model.TransferDetails
import com.vpdmoney.vpdmoneypay_kelviniyalo.databinding.ItemLayoutTransactionBinding

class TransactionsAdapter(
    private val context: Context,
    private val onItemClicked: (position: Int, itemAtPosition: TransferDetails) -> Unit
) : ListAdapter<TransferDetails, TransactionsAdapter.TransactionHistoryVH>(object :
    DiffUtil.ItemCallback<TransferDetails>() {

    override fun areItemsTheSame(oldItem: TransferDetails, newItem: TransferDetails): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TransferDetails, newItem: TransferDetails): Boolean {
        return oldItem == newItem
    }

}) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionHistoryVH {
        //inflate the view to be used by the payment option view holder
        val binding =
            ItemLayoutTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TransactionHistoryVH(binding, onItemClick = { position ->
            val itemAtPosition = currentList[position]
            this.onItemClicked(position, itemAtPosition)
        })

    }

    override fun getItemCount(): Int = currentList.size

    override fun onBindViewHolder(holder: TransactionHistoryVH, position: Int) {
        val itemAtPosition = currentList[position]
        holder.bind(itemAtPosition)
    }


    inner class TransactionHistoryVH(
        private val binding: ItemLayoutTransactionBinding,
        onItemClick: (position: Int) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onItemClick(adapterPosition)
            }
        }


        fun bind(transaction: TransferDetails) {
            with(binding) {
                transactionTitleTV.text = transaction.transactionTypeName
                transactionAmountTV.text = formatTransaction(transaction)
                transactionDateTV.text = transaction.transactionTime
                transactionStatusTV.text = transaction.status
                if (transaction.status.equals("Successful", true)) {
                    transactionStatusTV.setTextColor(context.resources.getColor(R.color.teal_700))
                } else {
                    transactionStatusTV.setTextColor(context.resources.getColor(R.color.red))
                }
                if (transaction.isCredit) {
                    transactionTypeIV.setImageResource(R.drawable.ic_iscredit)
                } else {
                    transactionTypeIV.setImageResource(R.drawable.ic_isdebit)
                }
            }

        }

        private fun formatTransaction(transaction: TransferDetails): String {
            return if (transaction.accountType.contains("Dollar", true)) "$" + Helper.formatAmount(
                transaction.amount
            )
            else "₦" + Helper.formatAmount(transaction.amount)
        }

    }
}