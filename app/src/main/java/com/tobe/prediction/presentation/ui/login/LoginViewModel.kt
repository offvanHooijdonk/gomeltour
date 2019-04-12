package com.tobe.prediction.presentation.ui.login

import android.content.Intent
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.tobe.prediction.helper.attachTo
import com.tobe.prediction.helper.schedulersIO
import com.tobe.prediction.model.auth.AuthGoogle
import com.tobe.prediction.presentation.ui.BaseViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject

class LoginViewModel(private val authGoogle: AuthGoogle) : BaseViewModel() {
    companion object {
        const val NAV_MAIN = "main"
    }
    override val cd = CompositeDisposable()

    val navigation = PublishSubject.create<String>() // todo use Cicero or Navigation

    val authIntent: Intent
        get() = authGoogle.getSignInClient().signInIntent

    val enableLoginButton = ObservableBoolean(false)
    val showLoginProgress = ObservableBoolean(false)
    val errorMessage = ObservableField<String>()

    fun activityStart() {
        showProgress()
        getAuthenticatedUser()
    }

    fun handleAuthData(data: Intent?) {
        if (data != null) {
            showProgress()
            authGoogle.signInUser(data)
                    .schedulersIO()
                    .subscribe({
                        hideAll()
                        navigateTo(NAV_MAIN)
                    }, { th ->
                        showError(th.toString())
                    }).attachTo(cd)
        } else {
            errorMessage.set("No auth data received") // todo use resources
        }
    }

    private fun getAuthenticatedUser() {
        showProgress()

        authGoogle.getLoggedUser()
                .schedulersIO()
                .subscribe({
                    hideAll()
                    navigateTo(NAV_MAIN)
                }, { th -> errorMessage.set(th.message) }, {
                    showLogin()
                }).attachTo(cd)
    }

    private fun navigateTo(key: String) {
        navigation.onNext(key)
    }

    private fun showProgress() {
        enableLoginButton.set(false)
        showLoginProgress.set(true)
        errorMessage.set(null)
    }

    private fun showLogin() {
        showLoginProgress.set(false)
        enableLoginButton.set(true)
    }

    private fun showError(msg: String) {
        showLoginProgress.set(false)
        enableLoginButton.set(true)
        errorMessage.set(msg)
    }

    private fun hideAll() {
        showLoginProgress.set(false)
        enableLoginButton.set(false)
        errorMessage.set(null)
    }
}