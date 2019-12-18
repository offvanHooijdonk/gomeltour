package com.tobe.prediction.presentation.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import com.tobe.prediction.app.App
import com.tobe.prediction.model.Session
import com.tobe.prediction.model.auth.AuthFirebase
import com.tobe.prediction.model.auth.AuthGoogle
import com.tobe.prediction.presentation.navigation.RouterHelper

class BottomOptionsViewModel(
        private val authGoogle: AuthGoogle,
        private val authFirebase: AuthFirebase,
        private val routerHelper: RouterHelper
) : ViewModel() {

    fun logOut() {
        authGoogle.signOut({
            authFirebase.signOut()
            Session.user = null
            routerHelper.navigateToLogin()
        }, {
            Log.e(App.LOGCAT, "Log Off failed", it)

            authFirebase.signOut()
            Session.user = null
            routerHelper.navigateToLogin()
        })
    }
}