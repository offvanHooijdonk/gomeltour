package com.tobe.prediction.presentation.presenter.login

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.tobe.prediction.R
import com.tobe.prediction.domain.UserBean
import com.tobe.prediction.model.Session
import com.tobe.prediction.presentation.ui.login.ILoginView

/**
 * Created by Yahor_Fralou on 9/17/2018 5:34 PM.
 */

class LoginPresenter(private val ctx: Context) {
    private var view: ILoginView? = null

    fun inject(view: ILoginView) {
        this.view = view
    }

    fun checkAccount() {
        val account = GoogleSignIn.getLastSignedInAccount(ctx)
        if (account == null) {
            view?.showLoginForm(true)
        } else {
            view?.showLoginForm(false)
            // todo authenticate and proceed to the app
        }
    }

    fun startAuth() {
        val gSignInClient = GoogleSignIn.getClient(ctx, prepareOptions())
        view?.startSignIn(gSignInClient.signInIntent)
    }

    private fun prepareOptions() = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(ctx.getString(R.string.web_client_id))
            .requestEmail()
            .build()

    fun handleSignInResponse(signedInTask: Task<GoogleSignInAccount>) {
        view?.showLoginProgress(true)
        try {
            val account: GoogleSignInAccount = signedInTask.getResult(ApiException::class.java)
            // todo create & save user
            Session.user = UserBean(account.id ?: "No ID", account.displayName
                    ?: "empty name")// fixme
            view?.showLoginProgress(false)
            // --
            view?.onUserAuthenticated(account) // todo this goes to RX subscriber and uses UserBean class
        } catch (e: ApiException) {
            view?.showLoginProgress(false)
            view?.showAuthError(e)
        }
    }

}