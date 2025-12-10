package com.rosafiesta.chat.infra.database.mappers

import com.rosafiesta.chat.domain.models.ChatParticipant
import com.rosafiesta.chat.infra.database.entities.ChatParticipantEntity

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