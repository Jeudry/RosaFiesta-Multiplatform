package com.jeudry.chat.presentation.create_chat

import com.jeudry.chat.domain.models.Chat

sealed interface CreateChatEvent {
    data class OnChatCreated(val chat: Chat): CreateChatEvent
}