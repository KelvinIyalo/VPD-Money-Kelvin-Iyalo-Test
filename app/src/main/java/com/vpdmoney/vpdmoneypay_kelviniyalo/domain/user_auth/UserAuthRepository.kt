package com.vpdmoney.vpdmoneypay_kelviniyalo.domain.user_auth

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

interface UserAuthRepository {

    fun userLogin(email:String,password:String) : Task<AuthResult>

    suspend fun userRegister(email:String,password:String) : Task<AuthResult>

    fun userLogout()
}