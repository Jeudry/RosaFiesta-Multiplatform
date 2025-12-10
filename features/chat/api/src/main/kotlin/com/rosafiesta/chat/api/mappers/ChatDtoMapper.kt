package com.rosafiesta.chat.api.mappers

import com.rosafiesta.chat.api.dto.ChatDto
import com.rosafiesta.chat.domain.models.Chat

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