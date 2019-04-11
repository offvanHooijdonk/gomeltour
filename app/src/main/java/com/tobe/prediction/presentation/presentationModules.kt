package com.tobe.prediction.presentation

import com.tobe.prediction.presentation.presenter.login.LoginPresenter
import org.koin.dsl.module

val presentationModule = module {
    single { LoginPresenter(get()) }
}