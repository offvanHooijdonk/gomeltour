package com.tobe.prediction.presentation.ui.main

import androidx.databinding.ObservableField
import com.tobe.prediction.model.Session
import com.tobe.prediction.model.auth.AuthFirebase
import com.tobe.prediction.model.auth.AuthGoogle
import com.tobe.prediction.presentation.navigation.RouterHelper
import com.tobe.prediction.presentation.ui.BaseViewModel
import io.reactivex.disposables.CompositeDisposable

class MainViewModel(
        private val authGoogle: AuthGoogle,
        private val authFirebase: AuthFirebase,
        private val routerHelper: RouterHelper
) : BaseViewModel() {
    override val cd = CompositeDisposable()

    val errorMsg = ObservableField<String>()

    fun viewStart() {
        routerHelper.navigateToList()
    }

    fun logOut() {
        authGoogle.signOut({
            authFirebase.signOut()
            Session.user = null
            routerHelper.navigateToLogin()
        }, {
            errorMsg.set(it.message) // todo better handling
        })
    }

    fun back() {
        routerHelper.navigateBack()
    }
}