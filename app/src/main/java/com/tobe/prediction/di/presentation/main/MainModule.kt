package com.tobe.prediction.di.presentation.main

import com.tobe.prediction.presentation.presenter.main.MainPresenter
import dagger.Module
import dagger.Provides

/**
 * Created by Yahor_Fralou on 9/27/2018 12:17 PM.
 */

@Module
class MainModule {
    @Provides
    @MainScope
    fun providePresenter() : MainPresenter = MainPresenter()
}