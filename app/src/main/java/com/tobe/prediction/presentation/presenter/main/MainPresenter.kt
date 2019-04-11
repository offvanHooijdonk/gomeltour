package com.tobe.prediction.presentation.presenter.main

import com.tobe.prediction.model.Session
import com.tobe.prediction.model.auth.AuthFirebase
import com.tobe.prediction.model.auth.AuthGoogle
import com.tobe.prediction.presentation.ui.IMainView

/**
 * Created by Yahor_Fralou on 9/19/2018 12:52 PM.
 */

class MainPresenter constructor(private val authGoogle: AuthGoogle, private val authFirebase: AuthFirebase) {

    private var view: IMainView? = null

    fun attachView(view: IMainView) {
        this.view = view
    }

    fun onLogoutSelected() {
        authGoogle.signOut({
            authFirebase.signOut()
            Session.user = null
            view?.navigateLogin()
        }, {
            view?.showError(it)
        })
    }

    fun detachView() {
        view = null
    }
}