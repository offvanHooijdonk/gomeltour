package com.tobe.prediction.presentation.navigation

import android.content.Context
import android.content.Intent
import com.tobe.prediction.presentation.ui.MainActivity
import ru.terrakok.cicerone.android.support.SupportAppScreen

class MainScreen : SupportAppScreen() {
    override fun getActivityIntent(context: Context?): Intent = Intent(context, MainActivity::class.java)
}