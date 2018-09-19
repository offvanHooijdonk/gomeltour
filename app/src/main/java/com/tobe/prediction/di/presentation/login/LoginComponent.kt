package com.tobe.prediction.di.presentation.login

import com.tobe.prediction.presentation.ui.login.LoginActivity
import dagger.Subcomponent

/**
 * Created by Yahor_Fralou on 9/19/2018 4:31 PM.
 */

@LoginScope
@Subcomponent(modules = [LoginModule::class])
interface LoginComponent {
    fun inject(view: LoginActivity)
}