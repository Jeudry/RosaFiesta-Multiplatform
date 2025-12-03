package com.rosafiesta.api.api.mappers

import com.rosafiesta.api.api.dto.ChatParticipantDto
import com.rosafiesta.api.domain.models.ChatParticipant

fun ChatParticipant.toDto(): ChatParticipantDto {
    return ChatParticipantDto(
        id = userId,
        username = username,
        email = email,
        profilePictureUrl = profilePictureUrl
    )
}