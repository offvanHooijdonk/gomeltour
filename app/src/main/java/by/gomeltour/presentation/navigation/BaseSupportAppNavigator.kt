package by.gomeltour.presentation.navigation

import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.*

open class BaseSupportAppNavigator(private val backStack: NavigationBackStack, activity: FragmentActivity?, fm: FragmentManager?, containerId: Int)
    : SupportAppNavigator(activity, fm, containerId) {

    private val screensSkipBack = setOf(Screens.Keys.ACCOUNTS.name, Screens.Keys.ACHIEVEMENT_EARNED.name, Screens.Keys.PREFERENCES.name)

    override fun applyCommand(command: Command?) {
        super.applyCommand(command)

        when (command) {
            is Forward -> command.screen.takeIf { it.screenKey !in screensSkipBack }?.let { backStack.handler.onForward(command) }
            is Replace -> command.screen.takeIf { it.screenKey !in screensSkipBack }?.let { backStack.handler.onReplace(command) }
            is Back -> backStack.handler.onBack()
            is BackTo -> backStack.handler.onBackTo(command)
        }
    }
}