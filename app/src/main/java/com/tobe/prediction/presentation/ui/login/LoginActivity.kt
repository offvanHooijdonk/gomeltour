package com.tobe.prediction.presentation.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.tobe.prediction.R
import com.tobe.prediction.databinding.ActLoginBinding
import kotlinx.android.synthetic.main.act_login.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator

/**
 * Created by Yahor_Fralou on 9/17/2018 5:33 PM.
 */

class LoginActivity : AppCompatActivity() {
    companion object {
        private const val REQUEST_CODE_GOOGLE_SIGN_IN = 1
    }

    private val viewModel: LoginViewModel by viewModel()
    private val navigatorHolder: NavigatorHolder by inject()
    private val navigator = SupportAppNavigator(this, 0) // todo try override and set Transition basing on some field in LoginViewModel

    private var isLoginProcessPassed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActLoginBinding>(this, R.layout.act_login)
        binding.model = viewModel

        btnGoogleSign.setOnClickListener {
            startAuth()
        }
    }

    override fun onStart() {
        super.onStart()

        navigatorHolder.setNavigator(navigator)
        viewModel.activityStart()
    }

    override fun onStop() {
        super.onStop()

        navigatorHolder.removeNavigator()
    }

    private fun startAuth() {
        startActivityForResult(viewModel.authIntent, REQUEST_CODE_GOOGLE_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_GOOGLE_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                viewModel.handleAuthData(data)
            }
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

}
