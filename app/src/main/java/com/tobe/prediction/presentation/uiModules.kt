package com.tobe.prediction.presentation

import android.content.Context
import com.tobe.prediction.R
import com.tobe.prediction.presentation.ui.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val uiModule = module {
    single(named(OPTION_POS_KEY)) { getInteger(get(), R.integer.option_positive) }
    single(named(OPTION_NEG_KEY)) { getInteger(get(), R.integer.option_negative) }

    viewModel { LoginViewModel(get()) }
}

const val OPTION_NEG_KEY = "option_negative"
const val OPTION_POS_KEY = "option_positive"