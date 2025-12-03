package com.jeudry.chat.data.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.jeudry.chat.data.participant.KtorChatParticipantService
import com.jeudry.chat.data.chat.KtorChatService
import com.jeudry.chat.data.chat.OfflineFirstChatRepository
import com.jeudry.chat.data.chat.WebSocketChatConnectionClient
import com.jeudry.chat.data.lifecycle.AppLifecycleObserver
import com.jeudry.chat.data.message.KtorChatMessageService
import com.jeudry.chat.data.message.OfflineFirstMessageRepository
import com.jeudry.chat.data.network.ConnectionErrorHandler
import com.jeudry.chat.data.network.ConnectionRetryHandler
import com.jeudry.chat.data.network.KtorWebSocketConnector
import com.jeudry.chat.data.notification.KtorDeviceTokenService
import com.jeudry.chat.data.participant.OfflineFirstChatParticipantRepository
import com.jeudry.chat.database.DatabaseFactory
import com.jeudry.chat.domain.chat.ChatConnectionClient
import com.jeudry.chat.domain.participant.ChatParticipantService
import com.jeudry.chat.domain.chat.ChatRepository
import com.jeudry.chat.domain.chat.ChatService
import com.jeudry.chat.domain.message.ChatMessageService
import com.jeudry.chat.domain.message.MessageRepository
import com.jeudry.chat.domain.notification.DeviceTokenService
import com.jeudry.chat.domain.participant.ChatParticipantRepository
import kotlinx.serialization.json.Json
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformChatDataModule: Module

val chatDataModule = module {
    includes(platformChatDataModule)

    singleOf(::KtorChatParticipantService) bind ChatParticipantService::class
    singleOf(::KtorChatService) bind ChatService::class
    singleOf(::OfflineFirstChatRepository) bind ChatRepository::class
    singleOf(::OfflineFirstMessageRepository) bind MessageRepository::class
    singleOf(::WebSocketChatConnectionClient) bind ChatConnectionClient::class
    singleOf(::ConnectionRetryHandler)
    singleOf(::KtorWebSocketConnector)
    singleOf(::KtorChatMessageService) bind ChatMessageService::class
    singleOf(::KtorDeviceTokenService) bind DeviceTokenService::class
    singleOf(::OfflineFirstChatParticipantRepository) bind ChatParticipantRepository::class
    single {
        Json {
            ignoreUnknownKeys = true
        }
    }
    single {
        get<DatabaseFactory>()
            .create()
            .setDriver(BundledSQLiteDriver())
            .build()
    }
}