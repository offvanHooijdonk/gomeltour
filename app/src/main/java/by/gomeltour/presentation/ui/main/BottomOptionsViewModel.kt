package by.gomeltour.presentation.ui.main

import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import by.gomeltour.app.App
import by.gomeltour.service.Session
import by.gomeltour.service.auth.AuthFirebase
import by.gomeltour.service.auth.AuthGoogle
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

    fun openSettings() {
        routerHelper.navigateToPreferences()
    }
}