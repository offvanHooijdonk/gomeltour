package by.gomeltour.service

import android.content.Context
import android.location.Geocoder
import by.gomeltour.service.auth.AuthFirebase
import by.gomeltour.service.auth.AuthGoogle
import by.gomeltour.service.event.EventService
import by.gomeltour.service.profile.ProfileService
import com.google.android.gms.location.LocationServices
import org.koin.dsl.module

val serviceModule = module {
    single { AuthGoogle(get(), get(), get()) }
    single { AuthFirebase() }
    single { EventService(get()) }
    single { ProfileService(get()) }
    single { LocationsService(get(), get()) }

    single { LocationServices.getFusedLocationProviderClient(get<Context>()) }
    single { Geocoder(get<Context>()) }
}