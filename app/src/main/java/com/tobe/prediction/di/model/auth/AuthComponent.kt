package com.tobe.prediction.di.model.auth

import com.tobe.prediction.di.presentation.login.LoginComponent
import dagger.Subcomponent

/**
 * Created by Yahor_Fralou on 9/19/2018 5:17 PM.
 */

@Subcomponent(modules = [AuthModule::class])
interface AuthComponent {
    fun plusLoginComponent(): LoginComponent
}