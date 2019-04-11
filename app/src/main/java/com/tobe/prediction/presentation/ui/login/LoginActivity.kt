package com.tobe.prediction.presentation.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.tobe.prediction.R
import com.tobe.prediction.databinding.ActLoginBinding
import com.tobe.prediction.helper.hide
import com.tobe.prediction.helper.hideBut
import com.tobe.prediction.helper.show
import com.tobe.prediction.presentation.ui.MainActivity
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.act_login.*
import org.jetbrains.anko.longToast
import org.jetbrains.anko.startActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by Yahor_Fralou on 9/17/2018 5:33 PM.
 */

class LoginActivity : AppCompatActivity() {
    companion object {
        private const val REQUEST_CODE_GOOGLE_SIGN_IN = 1
    }

    private val viewModel: LoginViewModel by viewModel()
    private lateinit var dispNavigation: Disposable

    private var isLoginProcessPassed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActLoginBinding>(this, R.layout.act_login)
        binding.model = viewModel

        dispNavigation = viewModel.navigation.subscribe {
            Toast.makeText(this, "Navigating to Main", Toast.LENGTH_LONG).show()
        }

        btnGoogleSign.setOnClickListener {
            startAuth()
        }
    }

    override fun onStart() {
        super.onStart()

        viewModel.activityStart()
    }

    override fun onDestroy() {
        super.onDestroy()

        dispNavigation.dispose()
    }

    private fun startAuth() {
        startActivityForResult(viewModel.authIntent, REQUEST_CODE_GOOGLE_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_GOOGLE_SIGN_IN) {
            if (resultCode == Activity.RESULT_OK) {
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
