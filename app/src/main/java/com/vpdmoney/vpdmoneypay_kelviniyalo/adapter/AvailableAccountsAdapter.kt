package com.vpdmoney.vpdmoneypay_kelviniyalo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vpdmoney.vpdmoneypay_kelviniyalo.R
import com.vpdmoney.vpdmoneypay_kelviniyalo.common.Helper
import com.vpdmoney.vpdmoneypay_kelviniyalo.data.model.UserAccounts
import com.vpdmoney.vpdmoneypay_kelviniyalo.databinding.AvailableAccountsBinding


class AvailableAccountsAdapter(
    private val context: Context,
    private val onItemClicked: (position: Int, itemAtPosition: UserAccounts) -> Unit
) : ListAdapter<UserAccounts, AvailableAccountsAdapter.TransactionHistoryVH>(object :
    DiffUtil.ItemCallback<UserAccounts>() {

    override fun areItemsTheSame(oldItem: UserAccounts, newItem: UserAccounts): Boolean {
        return oldItem.accountNumber == newItem.accountType
    }

    override fun areContentsTheSame(oldItem: UserAccounts, newItem: UserAccounts): Boolean {
        return oldItem == newItem
    }

}) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionHistoryVH {
        //inflate the view to be used by the payment option view holder
        val binding =
            AvailableAccountsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TransactionHistoryVH(binding, onItemClick = { position ->
            val itemAtPosition = currentList[position]
            this.onItemClicked(position, itemAtPosition)
        })

    }

    override fun submitList(list: List<UserAccounts>?) {
        super.submitList(list)

    }

    override fun getItemCount(): Int = currentList.size

    override fun onBindViewHolder(holder: TransactionHistoryVH, position: Int) {
        val itemAtPosition = currentList[position]
        holder.bind(itemAtPosition)
    }


    inner class TransactionHistoryVH(
        private val binding: AvailableAccountsBinding,
        onItemClick: (position: Int) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onItemClick(adapterPosition)
            }
        }


        fun bind(transaction: UserAccounts) {

            with(binding) {
                var isVisible = false
                this.accountType.text = transaction.accountType
                this.accountNumber.text = transaction.accountNumber

                updateAccountBalance(transaction, isVisible)

                val colorResId = when (transaction.accountType) {
                    "Current Account" -> R.color.blue
                    "Savings Account" -> R.color.purple
                    else -> R.color.gold
                }
                availableAccountBg.setBackgroundColor(context.getColor(colorResId))

                visibilityBtn.setOnClickListener {
                    isVisible = !isVisible
                    updateAccountBalance(transaction, isVisible)
                }
            }
        }

       private fun updateAccountBalance(transaction: UserAccounts, isVisible: Boolean) {
            binding.accountBalance.text = when {
                transaction.accountType.contains("Dollar", true) ->
                    if (isVisible) "$" + Helper.formatAmount(transaction.accountBalance) else "*******"

                else ->
                    if (isVisible) "₦" + Helper.formatAmount(transaction.accountBalance) else "*******"
            }
            binding.visibilityBtn.setImageResource(if (isVisible)R.drawable.baseline_visibility_off_24 else R.drawable.baseline_visibility_24)

        }


    }

}