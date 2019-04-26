package com.tobe.prediction.presentation.navigation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.tobe.prediction.presentation.ui.login.LoginActivity
import com.tobe.prediction.presentation.ui.main.MainActivity
import com.tobe.prediction.presentation.ui.predict.list.PredictListFragment
import com.tobe.prediction.presentation.ui.predict.view.PredictSingleFragment
import org.koin.core.KoinComponent
import org.koin.core.inject
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screens : KoinComponent {
    enum class Keys {
        MAIN, LOGIN, PREDICT_LIST, PREDICT_SINGLE
    }

    val mainScreen: MainScreen by inject()
    val loginScreen: LoginScreen by inject()
    val predictListScreen: PredictListScreen by inject()
    val predictSingleScreen: PredictSingleScreen by inject()
}

class MainScreen : SupportAppScreen() {
    override fun getActivityIntent(context: Context?): Intent = Intent(context, MainActivity::class.java)

    override fun getScreenKey(): String = Screens.Keys.MAIN.name
}

class LoginScreen : SupportAppScreen() {
    override fun getActivityIntent(context: Context?): Intent = Intent(context, LoginActivity::class.java).apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
    }

    override fun getScreenKey(): String = Screens.Keys.LOGIN.name
}

class PredictSingleScreen : SupportAppScreen() {
    var predictId: String = ""

    override fun getFragment(): Fragment {
        return PredictSingleFragment().apply { arguments = Bundle().apply { putString(PredictSingleFragment.EXTRA_PREDICT_ID, predictId) } }
    }

    override fun getScreenKey(): String = Screens.Keys.PREDICT_SINGLE.name
}

class PredictListScreen : SupportAppScreen() {
    override fun getFragment(): Fragment {
        return PredictListFragment()
    }

    override fun getScreenKey(): String = Screens.Keys.PREDICT_LIST.name
}