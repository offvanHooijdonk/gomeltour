package com.tobe.prediction.presentation.navigation

import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.*

open class BaseSupportAppNavigator(private val backStack: NavigationBackStack, activity: FragmentActivity?, fm: FragmentManager?, containerId: Int)
    : SupportAppNavigator(activity, fm, containerId) {

    override fun applyCommand(command: Command?) {
        super.applyCommand(command)

        when (command) {
            is Forward -> backStack.handler.onForward(command)
            is Replace -> backStack.handler.onReplace(command)
            is Back -> backStack.handler.onBack()
            is BackTo -> backStack.handler.onBackTo(command)
        }
    }
}