package com.jeudry.rosafiesta.di

import com.jeudry.auth.presentation.di.authPresentationModule
import com.jeudry.chat.data.di.chatDataModule
import com.jeudry.chat.presentation.di.chatPresentationModule
import com.jeudry.core.data.di.coreDataModule
import com.jeudry.core.presentation.di.corePresentationModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(
            coreDataModule,
            authPresentationModule,
            appModule,
            chatPresentationModule,
            corePresentationModule,
            chatDataModule
        )
    }
}