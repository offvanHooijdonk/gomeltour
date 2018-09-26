package com.tobe.prediction.presentation.presenter.login

import android.content.Context
import android.content.Intent
import com.tobe.prediction.helper.attachTo
import com.tobe.prediction.helper.schedulersIO
import com.tobe.prediction.model.auth.AuthGoogle
import com.tobe.prediction.presentation.ui.login.ILoginView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Yahor_Fralou on 9/17/2018 5:34 PM.
 */

class LoginPresenter @Inject constructor() {
    private val cd = CompositeDisposable()
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
                }, { th -> view?.showAuthError(th) }, { view?.showLoginForm(true) }).attachTo(cd)
    }

    fun onAuthSelected() {
        val gSignInClient = authGoogle.getSignInClient()
        view?.startSignIn(gSignInClient.signInIntent)
    }

    fun handleSignInResponse(signInData: Intent) {
        view?.showLoginProgress(true)
        authGoogle.signInUser(signInData)
                .compose(schedulersIO())
                .subscribe({ _ ->
                    view?.showLoginProgress(false)
                    view?.onUserAuthenticated()
                }, { th ->
                    view?.showLoginProgress(false)
                    view?.showAuthError(th)
                }).attachTo(cd)
    }

    fun detachView() {
        cd.dispose()
        view = null
    }

}