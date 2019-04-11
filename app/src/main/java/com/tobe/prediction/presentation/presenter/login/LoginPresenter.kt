package com.tobe.prediction.presentation.presenter.login

import android.content.Intent
import com.tobe.prediction.helper.attachTo
import com.tobe.prediction.helper.schedulersIO
import com.tobe.prediction.model.auth.AuthGoogle
import com.tobe.prediction.presentation.ui.login.ILoginView
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Yahor_Fralou on 9/17/2018 5:34 PM.
 */

class LoginPresenter(private val authGoogle: AuthGoogle) {
    private val cd = CompositeDisposable()

    private var view: ILoginView? = null

    fun attachView(view: ILoginView) {
        this.view = view
    }

    fun onViewVisible() {
        authenticateUser()
    }

    private fun authenticateUser() {
        authGoogle.getLoggedUser()
                .schedulersIO()
                .subscribe({
                    view?.showLoginForm(false)
                    view?.onUserAuthenticated()
                }, { th -> view?.showAuthError(th) }, { view?.showLoginForm(true) }).attachTo(cd)
    }

    fun onAuthSelected() {
        val gSignInClient = authGoogle.getSignInClient()
        view?.startSignIn(gSignInClient.signInIntent)
    }

    fun handleSignInResponse(signInData: Intent) {
        view?.showLoginProgress(true)
        authGoogle.signInUser(signInData)
                .schedulersIO()
                .subscribe({
                    view?.showLoginProgress(false)
                    view?.showLoginForm(false)
                    view?.onUserAuthenticated()
                }, { th ->
                    view?.showLoginProgress(false)
                    view?.showAuthError(th)
                }).attachTo(cd)
    }

    fun detachView() {
        cd.clear()
        view = null
    }

}