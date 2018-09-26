package com.tobe.prediction.di.model.auth

import com.tobe.prediction.model.auth.AuthFirebase
import com.tobe.prediction.model.auth.AuthGoogle
import dagger.Module
import dagger.Provides

/**
 * Created by Yahor_Fralou on 9/19/2018 5:17 PM.
 */

@Module
class AuthModule {
    @AuthScope
    @Provides
    fun provideGoogleAuth() = AuthGoogle()

    @AuthScope
    @Provides
    fun provideFirebaseAuth() = AuthFirebase()
}