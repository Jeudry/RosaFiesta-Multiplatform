package com.plcoding.rosafiesta.di

import com.plcoding.rosafiesta.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::MainViewModel)
    single {
        CoroutineScope(SupervisorJob() + Dispatchers.Default)
    }
}