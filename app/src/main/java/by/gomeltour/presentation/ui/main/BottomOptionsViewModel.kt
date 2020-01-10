package by.gomeltour.presentation.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import by.gomeltour.app.App
import by.gomeltour.model.Session
import by.gomeltour.model.auth.AuthFirebase
import by.gomeltour.model.auth.AuthGoogle
import by.gomeltour.presentation.navigation.RouterHelper

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