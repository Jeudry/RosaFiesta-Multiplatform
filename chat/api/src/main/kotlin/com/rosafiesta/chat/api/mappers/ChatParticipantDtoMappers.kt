package com.rosafiesta.chat.api.mappers

import com.rosafiesta.chat.api.dto.ChatParticipantDto
import com.rosafiesta.chat.domain.models.ChatParticipant

fun ChatParticipant.toDto(): ChatParticipantDto {
    return ChatParticipantDto(
        id = userId,
        username = username,
        email = email,
        profilePictureUrl = profilePictureUrl
    )
}