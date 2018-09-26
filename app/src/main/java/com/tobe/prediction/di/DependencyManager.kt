package com.tobe.prediction.di

import android.content.Context
import com.tobe.prediction.app.App
import com.tobe.prediction.di.app.AppComponent
import com.tobe.prediction.di.app.AppModule
import com.tobe.prediction.di.app.DaggerAppComponent
import com.tobe.prediction.di.presentation.login.LoginComponent

/**
 * Created by Yahor_Fralou on 9/19/2018 4:15 PM.
 */

class DependencyManager(ctx: Context) {
    companion object {
        val Names = DepNames
    }

    private val graph: AppComponent = DaggerAppComponent.builder()
            .appModule(AppModule(ctx))
            .build()

    fun loginComponent(): LoginComponent = graph.plusAuthComponent().plusLoginComponent()
}

fun dependency() = App.di

typealias DM = DependencyManager

object DepNames {
    const val REF_USERS = "users"
}