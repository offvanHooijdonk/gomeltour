package com.tobe.prediction.presentation.presenter.login

import android.content.Context
import android.content.Intent
import com.tobe.prediction.model.auth.AuthGoogle
import com.tobe.prediction.presentation.ui.login.ILoginView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Yahor_Fralou on 9/17/2018 5:34 PM.
 */

class LoginPresenter @Inject constructor() {
    @Inject
    lateinit var ctx: Context
    @Inject
    lateinit var authGoogle: AuthGoogle

    private var view: ILoginView? = null

    fun attachView(view: ILoginView) {
        this.view = view
    }

    fun onViewVisible() {
        authenticateUser()
    }

    private fun authenticateUser() {
        authGoogle.getLoggedUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ _ ->
                    view?.showLoginForm(false)
                    view?.onUserAuthenticated()
                }, { th -> view?.showAuthError(th) }, { view?.showLoginForm(true) })
    }

    fun onAuthSelected() {
        val gSignInClient = authGoogle.getSignInClient()
        view?.startSignIn(gSignInClient.signInIntent)
    }

    /*private fun prepareOptions() = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()*/

    fun handleSignInResponse(signInData: Intent) {
        view?.showLoginProgress(true)
        authGoogle.signInUser(signInData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ _ ->
                    view?.showLoginProgress(false)
                    view?.onUserAuthenticated()
                }, { th ->
                    view?.showLoginProgress(false)
                    view?.showAuthError(th)
                })
    }

    fun detachView() {
        view = null
    }

}