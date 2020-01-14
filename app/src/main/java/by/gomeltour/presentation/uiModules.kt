package by.gomeltour.presentation

import by.gomeltour.R
import by.gomeltour.presentation.navigation.*
import by.gomeltour.presentation.ui.login.LoginViewModel
import by.gomeltour.presentation.ui.main.BottomOptionsViewModel
import by.gomeltour.presentation.ui.main.EFABTransformer
import by.gomeltour.presentation.ui.main.MainViewModel
import by.gomeltour.presentation.ui.main.screenevents.ScreenEvent
import by.gomeltour.presentation.ui.event.list.EventListViewModel
import by.gomeltour.presentation.ui.event.view.EventSingleViewModel
import by.gomeltour.presentation.ui.profile.AccountsViewModel
import by.gomeltour.presentation.ui.profile.ProfileViewModel
import io.reactivex.subjects.PublishSubject
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

val viewModelModule = module {
    single(named(OPTION_POS_KEY)) { getInteger(get(), R.integer.option_positive) }
    single(named(OPTION_NEG_KEY)) { getInteger(get(), R.integer.option_negative) }
    factory(named(OPTION_POS_VALUE)) { androidContext().getString(R.string.form_option_positive) }
    factory(named(OPTION_NEG_VALUE)) { androidContext().getString(R.string.form_option_negative) }

    viewModel { LoginViewModel(get(), get()) }
    viewModel { MainViewModel(get(), get(named(MAIN_SCREEN_MANAGER))) }
    viewModel { BottomOptionsViewModel(get(), get(), get()) }
    viewModel { EventListViewModel(/*get(), */get(), get(named(MAIN_SCREEN_MANAGER))) }
    viewModel { EventSingleViewModel(/*get(),*/ get()) }
    viewModel { ProfileViewModel(get()) }
    viewModel { AccountsViewModel(get(), get()) }
}

val navModule = module {
    single { Cicerone.create() as CiceroneBase } /*bind Cicerone::class*/
    single { get<CiceroneBase>().navigatorHolder }
    single { get<CiceroneBase>().router }
    single { RouterHelper(get(), get(), get()) }
    single { NavigationBackStack() }

    // region Screens
    single { Screens() }
    single { LoginScreen() }
    single { MainScreen() }
    single { PreferencesScreen() }
    single { AccountsScreen() }
    single { OptionsDialogScreen() }
    single { EventListScreen() }
    single { EventSingleScreen() }
    single { ProfileScreen() }
    // endregion
}

val uiModule = module {
    single(named(MAIN_SCREEN_MANAGER)) { PublishSubject.create<ScreenEvent>() }
    single { params -> EFABTransformer(params[0], params[1]) }
}

typealias CiceroneBase = Cicerone<Router>

const val OPTION_NEG_KEY = "option_negative_key"
const val OPTION_POS_KEY = "option_positive_key"
const val OPTION_NEG_VALUE = "option_negative_value"
const val OPTION_POS_VALUE = "option_positive_value"

const val MAIN_SCREEN_MANAGER = "main_screen_manager"