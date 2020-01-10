package by.gomeltour.presentation.ui.main.screenevents

import androidx.annotation.StringRes

interface ScreenEvent // todo sealed class?

class ListScrollEvent(val isDown: Boolean) : ScreenEvent
class ErrorEvent(@StringRes stringRes: Int)