package by.gomeltour.presentation.navigation

import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router

class RouterHelper(private val router: Router, private val navigatorHolder: NavigatorHolder, private val screens: Screens) {
    fun navigateToMain() {
        router.navigateTo(screens.mainScreen)
    }

    fun navigateToPreferences() {
        router.navigateTo(screens.preferencesScreen)
    }

    fun navigateToLogin() {
        router.navigateTo(screens.loginScreen)
    }

    fun navigateToAccounts() {
        router.navigateTo(screens.accountScreen)
    }

    fun navigateToOptions() {
        router.navigateTo(screens.optionsDialogScreen)
    }

    fun navigateToEventList() {
        router.newRootScreen(screens.eventListScreen)
    }

    fun navigateToAchievements() {
        router.newRootScreen(screens.achievementsScreen)
    }

    fun navigateToEditSingle(eventId: String) {
        router.navigateTo(screens.eventSingleScreen.apply { this.eventId = eventId })
    }

    fun navigateToProfile(userId: String) {
        router.navigateTo(screens.profileScreen.apply { this.userId = userId })
    }

    fun navigateBack() {
        router.exit()
    }

}