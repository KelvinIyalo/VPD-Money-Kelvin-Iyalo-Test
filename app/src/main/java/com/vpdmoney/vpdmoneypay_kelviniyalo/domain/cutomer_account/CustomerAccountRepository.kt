package com.vpdmoney.vpdmoneypay_kelviniyalo.domain.cutomer_account

import com.vpdmoney.vpdmoneypay_kelviniyalo.data.model.UserAccounts

interface CustomerAccountRepository {

    fun getCustomerAccount():List<UserAccounts>

    fun getGetListOfBanks():List<String>

    fun getListOfAccountTypes():List<String>
}