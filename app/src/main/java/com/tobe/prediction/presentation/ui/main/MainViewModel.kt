package com.tobe.prediction.presentation.ui.main

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.tobe.prediction.helper.attachTo
import com.tobe.prediction.model.Session
import com.tobe.prediction.model.auth.AuthFirebase
import com.tobe.prediction.model.auth.AuthGoogle
import com.tobe.prediction.presentation.navigation.RouterHelper
import com.tobe.prediction.presentation.ui.BaseViewModel
import com.tobe.prediction.presentation.ui.main.screenevents.ListScrollEvent
import com.tobe.prediction.presentation.ui.main.screenevents.ScreenEvent
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable

class MainViewModel(
        private val authGoogle: AuthGoogle,
        private val authFirebase: AuthFirebase,
        private val routerHelper: RouterHelper,
        private val screenEvents: Observable<ScreenEvent>
) : BaseViewModel() {
    override val cd = CompositeDisposable()

    val errorMsg = ObservableField<String>()
    val extendState = ObservableBoolean(true)
    val showAddButton = ObservableBoolean(true)

    fun viewStart() {
        routerHelper.navigateToList()

        screenEvents.subscribe { event ->
            when (event) {
                is ListScrollEvent -> if (event.isDown) extendState.set(false) else extendState.set(true)
            }
        }.attachTo(cd)
    }

    fun showAddButton(isShow: Boolean) {
        showAddButton.set(isShow)
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