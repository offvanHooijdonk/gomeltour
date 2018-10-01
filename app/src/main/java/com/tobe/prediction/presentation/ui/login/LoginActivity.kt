package com.tobe.prediction.presentation.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tobe.prediction.R
import com.tobe.prediction.di.dependency
import com.tobe.prediction.helper.hide
import com.tobe.prediction.helper.show
import com.tobe.prediction.presentation.presenter.login.LoginPresenter
import com.tobe.prediction.presentation.ui.MainActivity
import kotlinx.android.synthetic.main.act_login.*
import org.jetbrains.anko.longToast
import org.jetbrains.anko.startActivity
import javax.inject.Inject

/**
 * Created by Yahor_Fralou on 9/17/2018 5:33 PM.
 */

class LoginActivity : AppCompatActivity(), ILoginView {
    companion object {
        private const val REQUEST_CODE_GOOGLE_SIGN_IN = 1
    }

    @Inject
    lateinit var presenter: LoginPresenter

    private var isLoginProcessPassed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_login)

        dependency().loginComponent().inject(this)
        presenter.attachView(this)

        blockSignIn.hide()
        pbLogin.hide()
        btnGoogleSign.setOnClickListener {
            isLoginProcessPassed = true
            presenter.onAuthSelected()
        }
    }

    override fun onStart() {
        super.onStart()

        presenter.onViewVisible()
    }

    override fun startSignIn(signInIntent: Intent) {
        startActivityForResult(signInIntent, REQUEST_CODE_GOOGLE_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_GOOGLE_SIGN_IN) {
            if (resultCode == Activity.RESULT_OK) {
                presenter.handleSignInResponse(data!!)
            } else {
                // todo show message
            }
        }
    }

    override fun showLoginForm(isShow: Boolean) {
        if (isShow) {
            blockSignIn.apply {
                alpha = 0.0f
                show()
                animate().alpha(1.0f).setDuration(350).start()
            }
        } else {
            blockSignIn.hide()
            pbLogin.hide()
        }
    }

    override fun onUserAuthenticated() {
        startActivity<MainActivity>()
        setTransitionToMain()
    }

    override fun showAuthError(e: Throwable) {
        longToast("Error: " + e.toString())
    }

    override fun showLoginProgress(isShow: Boolean) {
        if (isShow) {
            pbLogin.show()
            btnGoogleSign.isEnabled = false
        } else {
            pbLogin.hide()
            btnGoogleSign.isEnabled = true
        }
    }

    private fun setTransitionToMain() {
        val animEnter: Int
        val animLeave: Int
        if (isLoginProcessPassed) {
            animEnter = R.anim.screen_slide_rl_in
            animLeave = R.anim.screen_slide_rl_out
        } else {
            animEnter = android.R.anim.fade_in
            animLeave = android.R.anim.fade_out
        }
        overridePendingTransition(animEnter, animLeave)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }
}
