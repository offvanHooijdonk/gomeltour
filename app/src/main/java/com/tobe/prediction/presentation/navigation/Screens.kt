package com.tobe.prediction.presentation.navigation

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import com.tobe.prediction.presentation.ui.login.LoginActivity
import com.tobe.prediction.presentation.ui.main.MainActivity
import com.tobe.prediction.presentation.ui.predict.edit.PredictEditDialog
import com.tobe.prediction.presentation.ui.predict.edit.PredictEditViewModel
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