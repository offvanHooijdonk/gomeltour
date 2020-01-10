package by.gomeltour.helper

import org.koin.dsl.module

val helperModule = module {
    single { Configs() }
}