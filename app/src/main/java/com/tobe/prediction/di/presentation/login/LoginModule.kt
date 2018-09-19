package com.tobe.prediction.di.presentation.login

import com.tobe.prediction.presentation.presenter.login.LoginPresenter
import dagger.Module
import dagger.Provides

/**
 * Created by Yahor_Fralou on 9/19/2018 4:33 PM.
 */


@Module
class LoginModule {
    @Provides
    @LoginScope
    fun provideLoginPresenter(): LoginPresenter = LoginPresenter()

}