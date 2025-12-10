package com.rosafiesta.chat.api.mappers

import com.rosafiesta.chat.api.dto.ChatMessageDto
import com.rosafiesta.chat.domain.models.ChatMessage

fun ChatMessage.toDto(): ChatMessageDto {
    return ChatMessageDto(
        id = sender.userId,
        chatId = chatId,
        content = content,
        createdAt = createdAt,
        senderId = sender.userId
    )
}