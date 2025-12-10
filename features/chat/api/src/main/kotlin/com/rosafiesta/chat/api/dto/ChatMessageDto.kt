package com.rosafiesta.chat.api.dto

import com.rosafiesta.core.domain.types.ChatId
import com.rosafiesta.core.domain.types.ChatMessageId
import com.rosafiesta.core.domain.types.UserId
import java.time.Instant

data class ChatMessageDto(
    val id: ChatMessageId,
    val chatId: ChatId,
    val content: String,
    val createdAt: Instant,
    val senderId: UserId
)
