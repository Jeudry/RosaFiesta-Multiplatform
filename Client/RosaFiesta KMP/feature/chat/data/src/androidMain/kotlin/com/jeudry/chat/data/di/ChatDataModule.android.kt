package com.jeudry.chat.data.di

import com.jeudry.chat.data.lifecycle.AppLifecycleObserver
import com.jeudry.chat.data.network.ConnectionErrorHandler
import com.jeudry.chat.data.network.ConnectivityObserver
import com.jeudry.chat.data.notification.FirebasePushNotificationService
import com.jeudry.chat.database.DatabaseFactory
import com.jeudry.chat.domain.notification.PushNotificationService
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual val platformChatDataModule = module {
    single { DatabaseFactory(androidContext()) }
    singleOf(::AppLifecycleObserver)
    singleOf(::ConnectivityObserver)
    singleOf(::ConnectionErrorHandler)

    singleOf(::FirebasePushNotificationService) bind PushNotificationService::class
}