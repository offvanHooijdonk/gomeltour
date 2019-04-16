package com.tobe.prediction.presentation.navigation

import android.content.Context
import android.content.Intent
import com.tobe.prediction.presentation.ui.login.LoginActivity
import com.tobe.prediction.presentation.ui.main.MainActivity
import org.koin.core.KoinComponent
import org.koin.core.inject
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screens : KoinComponent {
    val mainScreen: MainScreen by inject()
    val loginScreen: LoginScreen by inject()
}

class MainScreen : SupportAppScreen() {
    override fun getActivityIntent(context: Context?): Intent = Intent(context, MainActivity::class.java)
}

class LoginScreen : SupportAppScreen() {
    override fun getActivityIntent(context: Context?): Intent = Intent(context, LoginActivity::class.java).apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
    }
}