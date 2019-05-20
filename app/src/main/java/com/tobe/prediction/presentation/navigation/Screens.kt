package com.tobe.prediction.presentation.navigation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.tobe.prediction.presentation.ui.login.LoginActivity
import com.tobe.prediction.presentation.ui.main.AccountsDialogFragment
import com.tobe.prediction.presentation.ui.main.BottomOptionsDialog
import com.tobe.prediction.presentation.ui.main.MainActivity
import com.tobe.prediction.presentation.ui.predict.edit.PredictEditDialog
import com.tobe.prediction.presentation.ui.predict.list.PredictListFragment
import com.tobe.prediction.presentation.ui.predict.view.PredictSingleFragment
import org.koin.core.KoinComponent
import org.koin.core.inject
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screens : KoinComponent {
    enum class Keys {
        LOGIN, MAIN, ACCOUNTS, OPTIONS, PREDICT_LIST, PREDICT_EDIT, PREDICT_SINGLE
    }

    val loginScreen: LoginScreen by inject()
    val mainScreen: MainScreen by inject()
    val accountScreen: AccountsScreen by inject()
    val predictListScreen: PredictListScreen by inject()
    val predictEditScreen: PredictEditScreen by inject()
    val predictSingleScreen: PredictSingleScreen by inject()
    val optionsDialogScreen: OptionsDialogScreen by inject()
}

abstract class BaseScreen(private val screenKeyValue: String) : SupportAppScreen() {
    override fun getScreenKey(): String = screenKeyValue
}

class MainScreen : BaseScreen(Screens.Keys.MAIN.name) {
    override fun getActivityIntent(context: Context?): Intent = Intent(context, MainActivity::class.java)
}

class AccountsScreen : BaseScreen(Screens.Keys.ACCOUNTS.name) {
    override fun getFragment(): Fragment = AccountsDialogFragment()
}

class OptionsDialogScreen : BaseScreen(Screens.Keys.OPTIONS.name) {
    override fun getFragment(): Fragment = BottomOptionsDialog()
}

class LoginScreen : BaseScreen(Screens.Keys.LOGIN.name) {
    override fun getActivityIntent(context: Context?): Intent = Intent(context, LoginActivity::class.java).apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
    }
}

class PredictSingleScreen : BaseScreen(Screens.Keys.PREDICT_SINGLE.name) {
    var predictId: String = ""

    override fun getFragment(): Fragment {
        return PredictSingleFragment().apply { arguments = Bundle().apply { putString(PredictSingleFragment.EXTRA_PREDICT_ID, predictId) } }
    }
}

class PredictEditScreen : BaseScreen(Screens.Keys.PREDICT_EDIT.name) {
    override fun getFragment(): Fragment = PredictEditDialog()
}

class PredictListScreen : BaseScreen(Screens.Keys.PREDICT_LIST.name) {
    override fun getFragment(): Fragment = PredictListFragment()
}