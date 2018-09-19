package com.tobe.prediction.model.auth

import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.tobe.prediction.domain.UserBean
import com.tobe.prediction.model.Session
import io.reactivex.Maybe
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by Yahor_Fralou on 9/18/2018 12:48 PM.
 */

class AuthGoogle @Inject constructor() {
    @Inject
    lateinit var ctx: Context

    fun getLoggedUser(): Maybe<UserBean> {
        val account = GoogleSignIn.getLastSignedInAccount(ctx)
        // todo get data from DB
        return if (account == null) {
            Maybe.empty()
        } else {
            Maybe.just(UserBean(account.id ?: "-", account.displayName ?: "-"))
        }

    }

    fun signInUser(signInData: Intent): Single<UserBean> {
        val task = GoogleSignIn.getSignedInAccountFromIntent(signInData)

        try {
            val account: GoogleSignInAccount = task.getResult(ApiException::class.java)
            // todo create & save user
            Session.user = UserBean(account.id ?: "No ID", account.displayName
                    ?: "empty name")// fixme
            return Single.just(Session.user)
        } catch (e: ApiException) {
            return Single.error(e)
        }
    }

    fun getSignInClient(): GoogleSignInClient = GoogleSignIn.getClient(ctx, prepareOptions())

    private fun prepareOptions(): GoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
}