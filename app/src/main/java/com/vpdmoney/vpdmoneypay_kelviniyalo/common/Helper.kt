package com.vpdmoney.vpdmoneypay_kelviniyalo.common

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.vpdmoney.vpdmoneypay_kelviniyalo.R
import com.vpdmoney.vpdmoneypay_kelviniyalo.data.model.UserAccounts
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import java.text.DecimalFormat

object Helper {

    fun View.showMessage(message: String) {
        Snackbar.make(this, message, Snackbar.LENGTH_LONG).show()
    }

    fun formatAmount(value: Any): String {
        val valueToBeFormatted: Number = if (value is String) {
            value.toDouble()
        } else {
            value as Number
        }
        val df = DecimalFormat("##,###,##0.00")
        return df.format(valueToBeFormatted)
    }

    fun View.startMoveUpAnimation(context: Context) {
        val animation = AnimationUtils.loadAnimation(context, R.anim.move_up)
        startAnimation(animation)
    }

    fun showLoadingDialog(context: Activity, message: String? = null): AlertDialog {
        val view = LayoutInflater.from(context).inflate(R.layout.loading_layout, null)
        val dialog = MaterialAlertDialogBuilder(context)
            .setView(view)
            .setCancelable(false)
            .show()
        val text = view.findViewById<TextView>(R.id.message)
        if (message?.isNullOrEmpty() == false) {
            text.text = message
        }

        return dialog
    }

    fun showSuccessAlertDialog(context: Activity, message: String? = null,onContinue:() ->Unit): AlertDialog {
        val view = LayoutInflater.from(context).inflate(R.layout.success_alert, null)
        val dialog = MaterialAlertDialogBuilder(context)
            .setView(view)
            .setCancelable(false)
            .show()
        val text = view.findViewById<TextView>(R.id.message_text)
        val continueBtn = view.findViewById<TextView>(R.id.continue_button)
        if (message?.isNullOrEmpty() == false) {
            text.text = message
        }
        continueBtn.setOnClickListener { onContinue.invoke() }

        return dialog
    }

    fun getCurrentDateTimeFormatted(): String {
        val currentDateTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a")
        return currentDateTime.format(formatter)
    }

    fun Context.hideKeyboard(view: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun formatAccountBalance(userAccount: UserAccounts):String{
        return if (userAccount.accountType.contains("Dollar")) {
            "$" + Helper.formatAmount(userAccount.accountBalance)
        } else {
            "₦" + Helper.formatAmount(userAccount.accountBalance)
        }
    }
}