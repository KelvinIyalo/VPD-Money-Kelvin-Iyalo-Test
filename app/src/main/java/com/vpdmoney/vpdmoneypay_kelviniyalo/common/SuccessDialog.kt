package com.vpdmoney.vpdmoneypay_kelviniyalo.common

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import com.vpdmoney.vpdmoneypay_kelviniyalo.R
import com.vpdmoney.vpdmoneypay_kelviniyalo.data.model.TransferDetails

fun Context.showSuccessDialog(response: TransferDetails, onContinueBtn: () -> Unit) {
    val dialogBuilder = AlertDialog.Builder(this)
    val popupView = LayoutInflater.from(this).inflate(R.layout.dialog_success, null)
    val date = popupView.findViewById<TextView>(R.id.card_payment_date)
    val amount = popupView.findViewById<TextView>(R.id.amount_text)
    val message = popupView.findViewById<TextView>(R.id.message_text)
    val proceedButton = popupView.findViewById<TextView>(R.id.dismiss_button)
    dialogBuilder.setView(popupView)
    dialogBuilder.setCancelable(false)
    val dialog = dialogBuilder.create()
        response.apply {
            val balanceSign = response.sourceBalance.take(1)
            date.text = transactionTime
            amount.text = "$balanceSign${Helper.formatAmount(response.amount)}"
            message.text = responseMessage
   }

    dialog.show()

    proceedButton.setOnClickListener {
        dialog.dismiss()
        onContinueBtn.invoke()
    }
}