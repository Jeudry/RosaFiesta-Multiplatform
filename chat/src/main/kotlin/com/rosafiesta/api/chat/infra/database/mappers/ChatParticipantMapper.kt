package com.rosafiesta.api.chat.infra.database.mappers

import com.rosafiesta.api.chat.domain.models.ChatParticipant
import com.rosafiesta.api.chat.infra.database.entities.ChatParticipantEntity

fun ChatParticipantEntity.toModel(): ChatParticipant {
    return ChatParticipant(
        userId = userId!!,
        username = username,
        email = email,
        profilePictureUrl = profilePictureUrl
    )
}

fun ChatParticipant.toEntity(): ChatParticipantEntity {
    return ChatParticipantEntity(
        userId = userId,
        username = username,
        email = email,
        profilePictureUrl = profilePictureUrl
    )
}