package com.tobe.prediction.presentation.presenter.main

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.tobe.prediction.model.Session
import com.tobe.prediction.presentation.ui.IMainView

/**
 * Created by Yahor_Fralou on 9/19/2018 12:52 PM.
 */

class MainPresenter(private val ctx: Context) {
    private var view: IMainView? = null

    fun inject(view: IMainView) {
        this.view = view
    }

    fun onLogoutSelected() {
        Session.user = null
        GoogleSignIn.getClient(ctx, GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
        ).signOut().addOnCompleteListener {
            view?.navigateLogin()
        }
    }
}