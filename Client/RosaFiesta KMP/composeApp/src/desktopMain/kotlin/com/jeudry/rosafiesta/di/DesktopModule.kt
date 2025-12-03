package com.jeudry.rosafiesta.di

import com.jeudry.rosafiesta.ApplicationStateHolder
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val desktopModule = module {
    singleOf(::ApplicationStateHolder)
}