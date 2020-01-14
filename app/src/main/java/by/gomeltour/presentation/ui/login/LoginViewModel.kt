package by.gomeltour.presentation.ui.login

import android.content.Intent
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import by.gomeltour.helper.attachTo
import by.gomeltour.helper.schedulersIO
import by.gomeltour.service.auth.AuthGoogle
import by.gomeltour.presentation.navigation.RouterHelper
import by.gomeltour.presentation.ui.BaseViewModel
import io.reactivex.disposables.CompositeDisposable

class LoginViewModel(private val authGoogle: AuthGoogle, private val routerHelper: RouterHelper) : BaseViewModel() {

    override val cd = CompositeDisposable()

    val authIntent: Intent
        get() = authGoogle.getSignInClient().signInIntent

    val enableLoginButton = ObservableBoolean(false)
    val showLoginProgress = ObservableBoolean(false)
    val errorMessage = ObservableField<String>()

    var isLoginProcessPassed = false

    fun activityStart() {
        isLoginProcessPassed = false
        showProgress()
        getAuthenticatedUser()
    }

    fun handleAuthData(data: Intent?) {
        if (data != null) {
            showProgress()
            authGoogle.signInUser(data)
                    .schedulersIO()
                    .subscribe({
                        isLoginProcessPassed = true
                        hideAll()
                        navigateToMain()
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
                    navigateToMain()
                }, { th -> errorMessage.set(th.message) }, {
                    showLogin()
                }).attachTo(cd)
    }

    private fun navigateToMain() {
        routerHelper.navigateToMain()
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