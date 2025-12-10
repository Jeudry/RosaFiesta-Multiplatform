package com.rosafiesta.api.chat.api.mappers

import com.rosafiesta.api.chat.api.dto.ChatMessageDto
import com.rosafiesta.api.chat.domain.models.ChatMessage

fun ChatMessage.toDto(): ChatMessageDto {
    return ChatMessageDto(
        id = sender.userId,
        chatId = chatId,
        content = content,
        createdAt = createdAt,
        senderId = sender.userId
    )
}