package com.tobe.prediction.presentation

import com.tobe.prediction.R
import com.tobe.prediction.presentation.navigation.*
import com.tobe.prediction.presentation.ui.login.LoginViewModel
import com.tobe.prediction.presentation.ui.main.MainViewModel
import com.tobe.prediction.presentation.ui.main.screenevents.ScreenEvent
import com.tobe.prediction.presentation.ui.predict.edit.PredictEditViewModel
import com.tobe.prediction.presentation.ui.predict.list.PredictListViewModel
import com.tobe.prediction.presentation.ui.predict.view.PredictSingleViewModel
import io.reactivex.subjects.PublishSubject
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

    single(named(MAIN_SCREEN_MANAGER)) { PublishSubject.create<ScreenEvent>() }

    viewModel { LoginViewModel(get(), get()) }
    viewModel { MainViewModel(get(), get(), get(), get(named(MAIN_SCREEN_MANAGER))) }
    viewModel { PredictListViewModel(get(), get(named(MAIN_SCREEN_MANAGER))) }
    viewModel { PredictEditViewModel(get()) }
    viewModel { PredictSingleViewModel(get()) }
}

val navModule = module {
    single { Cicerone.create() as CiceroneBase }
    single { get<CiceroneBase>().navigatorHolder }
    single { get<CiceroneBase>().router }
    single { RouterHelper(get(), get(), get()) }
    single { NavigationBackStack() }

    // region Screens
    single { Screens() }
    single { MainScreen() }
    single { LoginScreen() }
    single { PredictListScreen() }
    single { PredictSingleScreen() }
    // endregion
}

typealias CiceroneBase = Cicerone<Router>

const val OPTION_NEG_KEY = "option_negative_key"
const val OPTION_POS_KEY = "option_positive_key"
const val OPTION_NEG_VALUE = "option_negative_value"
const val OPTION_POS_VALUE = "option_positive_value"

const val MAIN_SCREEN_MANAGER = "main_screen_manager"