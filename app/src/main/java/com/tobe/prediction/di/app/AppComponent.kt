package com.tobe.prediction.di.app

import com.tobe.prediction.di.model.auth.AuthComponent
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Yahor_Fralou on 9/19/2018 4:20 PM.
 */

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    //fun plusLoginComponent(): LoginComponent
    fun plusAuthComponent(): AuthComponent
}