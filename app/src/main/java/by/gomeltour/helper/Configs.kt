package by.gomeltour.helper

import by.gomeltour.presentation.OPTION_NEG_KEY
import by.gomeltour.presentation.OPTION_NEG_VALUE
import by.gomeltour.presentation.OPTION_POS_KEY
import by.gomeltour.presentation.OPTION_POS_VALUE
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.qualifier.named

class Configs : KoinComponent {
    val optionPosIndex: Int by inject(named(OPTION_POS_KEY))
    val optionNegIndex: Int by inject(named(OPTION_NEG_KEY))
    val optionPosDefault: String by inject(named(OPTION_POS_VALUE))
    val optionNegDefault: String by inject(named(OPTION_NEG_VALUE))

}