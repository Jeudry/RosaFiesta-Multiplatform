package com.jeudry.chat.data.mappers

import com.jeudry.chat.data.dto.ChatParticipantDto
import com.jeudry.chat.database.entities.ChatParticipantEntity
import com.jeudry.chat.domain.models.ChatParticipant
import com.jeudry.core.domain.auth.User

fun ChatParticipantDto.toDomain(): ChatParticipant {
    return ChatParticipant(
        userId = userId,
        username = username,
        profilePictureUrl = profilePictureUrl
    )
}

fun ChatParticipantEntity.toDomain(): ChatParticipant {
    return ChatParticipant(
        userId = userId,
        username = username,
        profilePictureUrl = profilePictureUrl
    )
}

fun ChatParticipant.toEntity(): ChatParticipantEntity {
    return ChatParticipantEntity(
        userId = userId,
        username = username,
        profilePictureUrl = profilePictureUrl
    )
}