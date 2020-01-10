package by.gomeltour.model

import by.gomeltour.model.auth.AuthFirebase
import by.gomeltour.model.auth.AuthGoogle
import by.gomeltour.model.predict.PredictService
import by.gomeltour.model.profile.ProfileService
import org.koin.dsl.module

val serviceModule = module {
    single { AuthGoogle(get(), get(), get()) }
    single { AuthFirebase() }
    single { PredictService(get(), get(), get()) }
    single { ProfileService(get()) }
}