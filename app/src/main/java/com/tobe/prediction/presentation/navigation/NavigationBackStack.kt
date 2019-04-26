package com.tobe.prediction.presentation.navigation

import ru.terrakok.cicerone.Screen
import ru.terrakok.cicerone.commands.BackTo
import ru.terrakok.cicerone.commands.Forward
import ru.terrakok.cicerone.commands.Replace
import java.util.*

class NavigationBackStack {
    internal val handler = EventHandler()
    private val stack = Stack<Screen>()

    val currentScreen: Screen?
        get() = if (stack.isNotEmpty()) stack.peek() else null

    private fun addToBackStack(screen: Screen) {
        stack.push(screen)
    }

    private fun popBackStack() {
        if (stack.isNotEmpty()) stack.pop()
    }

    private fun popUntil(screen: Screen) {
        if (stack.contains(screen)) {
            while (stack.isNotEmpty() && stack.peek() != screen) stack.pop()
        }
    }

    internal inner class EventHandler {
        private val bs = this@NavigationBackStack

        fun onForward(command: Forward?) {
            command?.screen?.let { bs.addToBackStack(it) }

        }

        fun onReplace(command: Replace?) {
            command?.screen?.let { bs.popBackStack(); bs.addToBackStack(it) }
        }

        fun onBack() {
            bs.popBackStack()
        }

        fun onBackTo(command: BackTo?) {
            command?.screen?.let { bs.popUntil(it) }
        }
    }

}