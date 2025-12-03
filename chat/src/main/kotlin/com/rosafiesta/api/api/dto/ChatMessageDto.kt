package com.rosafiesta.api.api.dto

import com.rosafiesta.api.domain.types.ChatId
import com.rosafiesta.api.domain.types.ChatMessageId
import com.rosafiesta.api.domain.types.UserId
import java.time.Instant

data class ChatMessageDto(
    val id: ChatMessageId,
    val chatId: ChatId,
    val content: String,
    val createdAt: Instant,
    val senderId: UserId
)
