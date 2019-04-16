package com.tobe.prediction.presentation.navigation

import android.content.Context
import android.content.Intent
import com.tobe.prediction.presentation.ui.MainActivity
import org.koin.core.KoinComponent
import org.koin.core.inject
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screens : KoinComponent {
    val mainScreen: MainScreen by inject()
}

class MainScreen : SupportAppScreen() {
    override fun getActivityIntent(context: Context?): Intent = Intent(context, MainActivity::class.java)
}