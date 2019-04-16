package com.tobe.prediction.presentation

import com.tobe.prediction.R
import com.tobe.prediction.presentation.ui.login.LoginViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

const val PACKAGE_NAME = "package_name"

val uiModule = module {
    single(named(OPTION_POS_KEY)) { getInteger(get(), R.integer.option_positive) }
    single(named(OPTION_NEG_KEY)) { getInteger(get(), R.integer.option_negative) }
    single(named(PACKAGE_NAME)) { androidApplication().packageName }

    viewModel { LoginViewModel(get(), get()) }
}

val navModule = module {
    single { Cicerone.create() as CiceroneBase }
    single { get<CiceroneBase>().navigatorHolder }
    single { get<CiceroneBase>().router }
}

typealias CiceroneBase = Cicerone<Router>

const val OPTION_NEG_KEY = "option_negative"
const val OPTION_POS_KEY = "option_positive"