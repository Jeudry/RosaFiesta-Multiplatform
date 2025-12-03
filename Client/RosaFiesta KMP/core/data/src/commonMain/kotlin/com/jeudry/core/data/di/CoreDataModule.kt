package com.jeudry.core.data.di

import com.jeudry.core.data.auth.DataStoreSessionStorage
import com.jeudry.core.data.auth.KtorAuthService
import com.jeudry.core.data.logging.KermitLogger
import com.jeudry.core.data.networking.HttpClientFactory
import com.jeudry.core.domain.auth.AuthService
import com.jeudry.core.domain.auth.SessionStorage
import com.jeudry.core.domain.logging.RosaFiestaLogger
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformCoreDataModule: Module

val coreDataModule = module {
    includes(platformCoreDataModule)
    single<RosaFiestaLogger> { KermitLogger }
    single {
        HttpClientFactory(get(), get()).create(get())
    }
    singleOf(::KtorAuthService) bind AuthService::class
    singleOf(::DataStoreSessionStorage) bind SessionStorage::class
}