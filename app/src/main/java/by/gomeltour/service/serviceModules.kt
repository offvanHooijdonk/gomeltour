package by.gomeltour.service

import by.gomeltour.service.auth.AuthFirebase
import by.gomeltour.service.auth.AuthGoogle
import by.gomeltour.service.event.EventService
import by.gomeltour.service.profile.ProfileService
import org.koin.dsl.module

val serviceModule = module {
    single { AuthGoogle(get(), get(), get()) }
    single { AuthFirebase() }
    single { EventService(get()) }
    single { ProfileService(get()) }
}