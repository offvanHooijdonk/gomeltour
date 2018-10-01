package com.tobe.prediction.di.presentation.main

import com.tobe.prediction.presentation.ui.MainActivity
import dagger.Subcomponent

/**
 * Created by Yahor_Fralou on 9/27/2018 12:16 PM.
 */

@Subcomponent(modules = [MainModule::class])
@MainScope
interface MainComponent {
    fun inject(view: MainActivity)
}