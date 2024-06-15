package com.vpdmoney.vpdmoneypay_kelviniyalo.data.user_auth

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.vpdmoney.vpdmoneypay_kelviniyalo.domain.user_auth.UserAuthRepository

class UserAuthRepositoryImpl : UserAuthRepository {

    override fun userLogin(email: String, password: String) : Task<AuthResult> {
     return FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
    }

    override suspend fun userRegister(email: String, password: String): Task<AuthResult> {
        return FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
    }

    override fun userLogout() {
        FirebaseAuth.getInstance().signOut()
    }
}