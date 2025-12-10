package com.rosafiesta.chat.api.dto

import com.rosafiesta.api.core.domain.types.ChatId
import java.time.Instant

data class ChatDto(
    val id: ChatId,
    val participants: List<ChatParticipantDto>,
    val lastActivityAt: Instant,
    val lastMessage: ChatMessageDto?,
    val creator: ChatParticipantDto,
)
