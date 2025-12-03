package com.rosafiesta.api.api.mappers

import com.rosafiesta.api.api.dto.ChatDto
import com.rosafiesta.api.domain.models.Chat

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