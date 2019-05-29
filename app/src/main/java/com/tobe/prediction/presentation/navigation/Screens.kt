package com.tobe.prediction.presentation.navigation

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import com.tobe.prediction.presentation.ui.login.LoginActivity
import com.tobe.prediction.presentation.ui.profile.AccountsDialogFragment
import com.tobe.prediction.presentation.ui.main.BottomOptionsDialog
import com.tobe.prediction.presentation.ui.main.MainActivity
import com.tobe.prediction.presentation.ui.predict.edit.PredictEditDialog
import com.tobe.prediction.presentation.ui.predict.game.GameFragment
import com.tobe.prediction.presentation.ui.predict.list.PredictListFragment
import com.tobe.prediction.presentation.ui.predict.view.PredictSingleFragment
import com.tobe.prediction.presentation.ui.profile.ProfileFragment
import org.koin.core.KoinComponent
import org.koin.core.inject
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screens : KoinComponent {
    enum class Keys {
        LOGIN, MAIN, ACCOUNTS, OPTIONS, PREDICT_LIST, PREDICT_EDIT, PREDICT_SINGLE, PROFILE, GAME
    }

    val loginScreen: LoginScreen by inject()
    val mainScreen: MainScreen by inject()
    val accountScreen: AccountsScreen by inject()
    val predictListScreen: PredictListScreen by inject()
    val predictEditScreen: PredictEditScreen by inject()
    val predictSingleScreen: PredictSingleScreen by inject()
    val optionsDialogScreen: OptionsDialogScreen by inject()
    val profileScreen: ProfileScreen by inject()
    val gameScreen: GameScreen by inject()
}

abstract class BaseScreen(private val screenKeyValue: Screens.Keys) : SupportAppScreen() {
    override fun getScreenKey(): String = screenKeyValue.name
}

class MainScreen : BaseScreen(Screens.Keys.MAIN) {
    override fun getActivityIntent(context: Context?): Intent = Intent(context, MainActivity::class.java)
}

class AccountsScreen : BaseScreen(Screens.Keys.ACCOUNTS) {
    override fun getFragment(): Fragment = AccountsDialogFragment()
}

class OptionsDialogScreen : BaseScreen(Screens.Keys.OPTIONS) {
    override fun getFragment(): Fragment = BottomOptionsDialog()
}

class LoginScreen : BaseScreen(Screens.Keys.LOGIN) {
    override fun getActivityIntent(context: Context?): Intent = Intent(context, LoginActivity::class.java).apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
    }
}

class PredictSingleScreen : BaseScreen(Screens.Keys.PREDICT_SINGLE) {
    var predictId: String = ""

    override fun getFragment(): Fragment = PredictSingleFragment.instance(predictId)
}

class PredictEditScreen : BaseScreen(Screens.Keys.PREDICT_EDIT) {
    override fun getFragment(): Fragment = PredictEditDialog()
}

class PredictListScreen : BaseScreen(Screens.Keys.PREDICT_LIST) {
    override fun getFragment(): Fragment = PredictListFragment()
}

class ProfileScreen : BaseScreen(Screens.Keys.PROFILE) {
    var userId = ""

    override fun getFragment(): Fragment = ProfileFragment.instance(userId)
}

class  GameScreen : BaseScreen(Screens.Keys.GAME) {
    override fun getFragment(): Fragment = GameFragment.instance()
}