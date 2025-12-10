package com.rosafiesta.chat.domain.models

import com.rosafiesta.api.core.domain.types.ChatId
import java.time.Instant

data class Chat(
    val id: ChatId,
    val participants: Set<ChatParticipant>,
    val lastMessage: ChatMessage?,
    val creator: ChatParticipant,
    val lastActivityAt: Instant,
    val createdAt: Instant,
)
