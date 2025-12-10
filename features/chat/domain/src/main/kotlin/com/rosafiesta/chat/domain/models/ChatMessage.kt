package com.rosafiesta.chat.domain.models

import com.rosafiesta.core.domain.types.ChatId
import com.rosafiesta.core.domain.types.ChatMessageId
import java.time.Instant

data class ChatMessage(
    val id: ChatMessageId,
    val chatId: ChatId,
    val sender: ChatParticipant,
    val content: String,
    val createdAt: Instant
)