package com.tobe.prediction.helper

import org.koin.dsl.module

val helperModule = module {
    single { Configs() }
}