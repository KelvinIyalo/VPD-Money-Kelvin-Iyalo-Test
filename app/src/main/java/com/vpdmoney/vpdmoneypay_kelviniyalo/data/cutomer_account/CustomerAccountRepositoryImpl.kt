package com.vpdmoney.vpdmoneypay_kelviniyalo.data.cutomer_account

import com.vpdmoney.vpdmoneypay_kelviniyalo.common.Constants
import com.vpdmoney.vpdmoneypay_kelviniyalo.data.model.UserAccounts
import com.vpdmoney.vpdmoneypay_kelviniyalo.domain.cutomer_account.CustomerAccountRepository
import com.vpdmoney.vpdmoneypay_kelviniyalo.domain.sharedPreference.DataManager
import javax.inject.Inject

class CustomerAccountRepositoryImpl @Inject constructor(private val dataManager: DataManager) :
    CustomerAccountRepository {
    override fun getCustomerAccount(): List<UserAccounts> {
        return listOf<UserAccounts>(
            UserAccounts(
                "Current Account",
                accountNumber = "2345068953",
                accountBalance = dataManager.getSavedData(Constants.CURRENT_AMOUNT)?.toDouble()
                    ?: 0.1,
                tag = Constants.CURRENT_AMOUNT
            ),
            UserAccounts(
                "Savings Account",
                accountNumber = "2333218953",
                accountBalance = dataManager.getSavedData(Constants.SAVINGS_AMOUNT)?.toDouble()
                    ?: 0.2,
                tag = Constants.SAVINGS_AMOUNT
            ),
            UserAccounts(
                "Dollar Account",
                accountNumber = "2454429021",
                accountBalance = dataManager.getSavedData(Constants.DOLLAR_AMOUNT)?.toDouble()
                    ?: 0.3,
                tag = Constants.DOLLAR_AMOUNT
            )
        )
    }

    override fun getGetListOfBanks(): List<String> {
        return listOf("ACCESS", "ZENITH", "UBA", "VPD MONEY")
    }

    override fun getListOfAccountTypes(): List<String> {
        return getCustomerAccount().map { it.accountType }
    }


}