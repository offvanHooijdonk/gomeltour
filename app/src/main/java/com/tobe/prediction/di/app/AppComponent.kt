package com.tobe.prediction.di.app

import com.tobe.prediction.di.presentation.login.LoginComponent
import com.tobe.prediction.di.presentation.main.MainComponent
import com.tobe.prediction.di.presentation.predict.PredictComponent
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Yahor_Fralou on 9/19/2018 4:20 PM.
 */

@Singleton
@Component(modules = [AppModule::class, DaoModule::class])
interface AppComponent {

    fun plusLoginComponent(): LoginComponent
    fun plusMainComponent(): MainComponent
    fun plusPredictComponent(): PredictComponent
}