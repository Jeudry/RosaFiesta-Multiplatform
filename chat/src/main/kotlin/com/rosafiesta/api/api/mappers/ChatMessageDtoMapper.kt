package com.rosafiesta.api.api.mappers

import com.rosafiesta.api.api.dto.ChatMessageDto
import com.rosafiesta.api.domain.models.ChatMessage

fun ChatMessage.toDto(): ChatMessageDto {
    return ChatMessageDto(
        id = sender.userId,
        chatId = chatId,
        content = content,
        createdAt = createdAt,
        senderId = sender.userId
    )
}