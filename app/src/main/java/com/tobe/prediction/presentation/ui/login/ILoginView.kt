package com.tobe.prediction.presentation.ui.login

import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

/**
 * Created by Yahor_Fralou on 9/18/2018 12:49 PM.
 */

interface ILoginView {
    fun startSignIn(signInIntent: Intent)
    fun showLoginForm(isShow: Boolean)
    fun onUserAuthenticated(account: GoogleSignInAccount)
    fun showAuthError(e: Throwable)
    fun showLoginProgress(isShow: Boolean)
}