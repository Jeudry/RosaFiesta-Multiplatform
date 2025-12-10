package com.rosafiesta.api.chat.api.mappers

import com.rosafiesta.api.chat.api.dto.ChatParticipantDto
import com.rosafiesta.api.chat.domain.models.ChatParticipant

fun ChatParticipant.toDto(): ChatParticipantDto {
    return ChatParticipantDto(
        id = userId,
        username = username,
        email = email,
        profilePictureUrl = profilePictureUrl
    )
}