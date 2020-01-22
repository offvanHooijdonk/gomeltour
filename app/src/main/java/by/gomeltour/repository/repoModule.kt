package by.gomeltour.repository

import org.koin.dsl.module

val repoModule = module {
    single { EventRepo(get()) }
    single { LocationRepo(get()) }
    single { AchievementsRepo(get()) }
    single { CheckInRepo(get()) }
}