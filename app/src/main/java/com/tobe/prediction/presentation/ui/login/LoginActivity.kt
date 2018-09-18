package com.tobe.prediction.presentation.ui.login

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.tobe.prediction.R
import com.tobe.prediction.helper.hide
import com.tobe.prediction.helper.show
import com.tobe.prediction.presentation.presenter.login.LoginPresenter
import com.tobe.prediction.presentation.ui.MainActivity
import kotlinx.android.synthetic.main.act_login.*
import org.jetbrains.anko.longToast

/**
 * Created by Yahor_Fralou on 9/17/2018 5:33 PM.
 */

class LoginActivity : AppCompatActivity(), ILoginView {
    companion object {

        private const val RESULT_CODE_GOOGLE_SIGN_IN = 1
    }
    private lateinit var presenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_login)

        presenter = LoginPresenter(this) // todo replace with Dagger inject
        presenter.inject(this)

        blockSignIn.hide()
        pbLogin.hide()
        btnGoogleSign.setOnClickListener { presenter.startAuth() }
    }

    override fun onStart() {
        super.onStart()

        presenter.checkAccount()
    }

    override fun startSignIn(signInIntent: Intent) {
        startActivityForResult(signInIntent, RESULT_CODE_GOOGLE_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RESULT_CODE_GOOGLE_SIGN_IN) {
            presenter.handleSignInResponse(GoogleSignIn.getSignedInAccountFromIntent(data))
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

    override fun onUserAuthenticated(account: GoogleSignInAccount) {
        // todo make sure it's removed from task top and history
        // todo setup easy animation
        startActivity(Intent(this, MainActivity::class.java))
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
}
