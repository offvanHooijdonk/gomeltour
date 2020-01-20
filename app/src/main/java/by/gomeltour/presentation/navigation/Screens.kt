package by.gomeltour.presentation.navigation

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import by.gomeltour.presentation.ui.achievements.AchievmentsFragment
import by.gomeltour.presentation.ui.login.LoginActivity
import by.gomeltour.presentation.ui.profile.AccountsDialogFragment
import by.gomeltour.presentation.ui.main.BottomOptionsDialog
import by.gomeltour.presentation.ui.main.MainActivity
import by.gomeltour.presentation.ui.event.list.EventListFragment
import by.gomeltour.presentation.ui.event.view.EventSingleFragment
import by.gomeltour.presentation.ui.preferences.PreferencesActivity
import by.gomeltour.presentation.ui.profile.ProfileFragment
import org.koin.core.KoinComponent
import org.koin.core.inject
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screens : KoinComponent {
    enum class Keys {
        LOGIN, PREFERENCES, MAIN, ACCOUNTS, OPTIONS, EVENT_LIST, ACHIEVEMENTS, EVENT_SINGLE, PROFILE
    }

    val loginScreen: LoginScreen by inject()
    val preferencesScreen: PreferencesScreen by inject()
    val mainScreen: MainScreen by inject()
    val accountScreen: AccountsScreen by inject()
    val eventListScreen: EventListScreen by inject()
    val achievementsScreen: AchievementsScreen by inject()
    val eventSingleScreen: EventSingleScreen by inject()
    val optionsDialogScreen: OptionsDialogScreen by inject()
    val profileScreen: ProfileScreen by inject()
}

abstract class BaseScreen(private val screenKeyValue: Screens.Keys) : SupportAppScreen() {
    override fun getScreenKey(): String = screenKeyValue.name
}

class PreferencesScreen : BaseScreen(Screens.Keys.PREFERENCES) {
    override fun getActivityIntent(context: Context?): Intent = Intent(context, PreferencesActivity::class.java)
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

class EventSingleScreen : BaseScreen(Screens.Keys.EVENT_SINGLE) {
    var eventId: String = ""

    override fun getFragment(): Fragment = EventSingleFragment.instance(eventId)
}

class EventListScreen : BaseScreen(Screens.Keys.EVENT_LIST) {
    override fun getFragment(): Fragment = EventListFragment()
}

class AchievementsScreen : BaseScreen(Screens.Keys.ACHIEVEMENTS) {
    override fun getFragment(): Fragment = AchievmentsFragment()
}

class ProfileScreen : BaseScreen(Screens.Keys.PROFILE) {
    var userId = ""

    override fun getFragment(): Fragment = ProfileFragment.instance(userId)
}