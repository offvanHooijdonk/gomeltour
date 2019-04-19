package com.tobe.prediction.helper

import com.tobe.prediction.presentation.OPTION_NEG_KEY
import com.tobe.prediction.presentation.OPTION_NEG_VALUE
import com.tobe.prediction.presentation.OPTION_POS_KEY
import com.tobe.prediction.presentation.OPTION_POS_VALUE
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.qualifier.named

class Configs : KoinComponent {
    val optionPosIndex: Int by inject(named(OPTION_POS_KEY))
    val optionNegIndex: Int by inject(named(OPTION_NEG_KEY))
    val optionPosDefault: String by inject(named(OPTION_POS_VALUE))
    val optionNegDefault: String by inject(named(OPTION_NEG_VALUE))

}