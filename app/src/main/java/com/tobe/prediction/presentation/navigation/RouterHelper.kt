package com.tobe.prediction.presentation.navigation

import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router

class RouterHelper(private val router: Router, private val navigatorHolder: NavigatorHolder, private val screens: Screens) {
    fun navigateToMain() {
        router.navigateTo(screens.mainScreen)
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

    fun navigateToList() {
        router.newRootScreen(screens.predictListScreen)
    }

    fun navigateToPredictEdit() {
        router.navigateTo(screens.predictEditScreen)
    }

    fun navigateToPredictSingle(predictId: String) {
        router.navigateTo(screens.predictSingleScreen.apply { this.predictId = predictId })
    }

    fun navigateBack() {
        router.exit()
    }

}