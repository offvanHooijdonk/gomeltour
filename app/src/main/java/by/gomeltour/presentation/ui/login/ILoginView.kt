package by.gomeltour.presentation.ui.login

import android.content.Intent

/**
 * Created by Yahor_Fralou on 9/18/2018 12:49 PM.
 */

interface ILoginView {
    fun startSignIn(signInIntent: Intent)
    fun showLoginForm(isShow: Boolean)
    fun onUserAuthenticated()
    fun showAuthError(e: Throwable)
    fun showLoginProgress(isShow: Boolean)
}