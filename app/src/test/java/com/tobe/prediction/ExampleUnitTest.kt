package com.tobe.prediction

import android.app.Application
import android.content.Context
import android.content.res.Resources
import com.tobe.prediction.app.allModules
import com.tobe.prediction.dao.daoModule
import com.tobe.prediction.helper.helperModule
import com.tobe.prediction.model.serviceModule
import com.tobe.prediction.presentation.getInt
import com.tobe.prediction.presentation.navModule
import com.tobe.prediction.presentation.uiModule
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.koinApplication
import org.koin.test.check.checkModules
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

class ExampleUnitTest {

    @Mock
    private lateinit var mockApplication: Application

    @Mock
    private lateinit var mockResources: Resources

    @Before
    fun before() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun test_KoinModules() {
        Mockito.`when`(mockApplication.resources).thenReturn(mockResources)
        Mockito.`when`(mockResources.getInteger(Mockito.anyInt())).thenReturn(0)
        Mockito.`when`(mockApplication.getString(Mockito.anyInt())).thenReturn("0")

        startKoin {
            androidContext(mockApplication)
            modules(allModules)
        }.checkModules()
    }
}
