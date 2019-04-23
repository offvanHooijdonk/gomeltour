package com.tobe.prediction.presentation

import com.tobe.prediction.R
import com.tobe.prediction.presentation.navigation.LoginScreen
import com.tobe.prediction.presentation.navigation.MainScreen
import com.tobe.prediction.presentation.navigation.RouterHelper
import com.tobe.prediction.presentation.navigation.Screens
import com.tobe.prediction.presentation.ui.login.LoginViewModel
import com.tobe.prediction.presentation.ui.main.MainViewModel
import com.tobe.prediction.presentation.ui.predict.edit.PredictEditViewModel
import com.tobe.prediction.presentation.ui.predict.list.PredictListViewModel
import com.tobe.prediction.presentation.ui.predict.view.PredictSingleViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

val uiModule = module {
    single(named(OPTION_POS_KEY)) { getInteger(get(), R.integer.option_positive) }
    single(named(OPTION_NEG_KEY)) { getInteger(get(), R.integer.option_negative) }
    factory(named(OPTION_POS_VALUE)) { androidContext().getString(R.string.form_option_positive) }
    factory(named(OPTION_NEG_VALUE)) { androidContext().getString(R.string.form_option_negative) }

    viewModel { LoginViewModel(get(), get()) }
    viewModel { MainViewModel(get(), get(), get()) }
    viewModel { PredictListViewModel(get()) }
    viewModel { PredictEditViewModel(get()) }
    viewModel { PredictSingleViewModel(get()) }
}

val navModule = module {
    single { Cicerone.create() as CiceroneBase }
    single { get<CiceroneBase>().navigatorHolder }
    single { get<CiceroneBase>().router }
    single { RouterHelper(get(), get()) }

    // region Screens
    single { Screens() }
    single { MainScreen() }
    single { LoginScreen() }
    // endregion
}

typealias CiceroneBase = Cicerone<Router>

const val OPTION_NEG_KEY = "option_negative_key"
const val OPTION_POS_KEY = "option_positive_key"
const val OPTION_NEG_VALUE = "option_negative_value"
const val OPTION_POS_VALUE = "option_positive_value"