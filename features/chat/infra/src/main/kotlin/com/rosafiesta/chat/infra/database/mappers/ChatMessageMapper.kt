package com.rosafiesta.chat.infra.database.mappers

import com.rosafiesta.chat.domain.models.ChatMessage
import com.rosafiesta.chat.infra.database.entities.ChatMessageEntity

fun ChatMessageEntity.toModel(): ChatMessage {
    return ChatMessage(
        id = id!!,
        chatId = chatId!!,
        sender = sender!!.toModel(),
        content = content,
        createdAt = createdAt
    )
}