package com.tobe.prediction.model

import com.tobe.prediction.model.auth.AuthFirebase
import com.tobe.prediction.model.auth.AuthGoogle
import com.tobe.prediction.model.predict.PredictService
import org.koin.dsl.module

val serviceModule = module {
    single { AuthGoogle(get(), get(), get()) }
    single { AuthFirebase() }
    single { PredictService(get(), get()) }
}