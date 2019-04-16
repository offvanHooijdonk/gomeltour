package com.tobe.prediction.presentation.navigation

import ru.terrakok.cicerone.Router

class RouterHelper(private val router: Router, private val screens: Screens) {
    fun navigateToMain() {
        router.navigateTo(screens.mainScreen)
    }

    fun navigateToLogin() {
        router.navigateTo(screens.loginScreen)
    }
}