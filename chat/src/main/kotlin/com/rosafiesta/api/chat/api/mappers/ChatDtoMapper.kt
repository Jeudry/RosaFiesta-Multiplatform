package com.rosafiesta.api.chat.api.mappers

import com.rosafiesta.api.chat.api.dto.ChatDto
import com.rosafiesta.api.chat.domain.models.Chat

fun Chat.toDto(): ChatDto {
    return ChatDto(
        id = id,
        participants = participants.map {
            it.toDto()
        },
        lastActivityAt = lastActivityAt,
        lastMessage = lastMessage?.toDto(),
        creator = creator.toDto(),
    )
}