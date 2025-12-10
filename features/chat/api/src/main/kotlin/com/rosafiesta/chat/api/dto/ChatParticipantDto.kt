package com.rosafiesta.chat.api.dto

import com.rosafiesta.core.domain.types.UserId

data class ChatParticipantDto(
    val id: UserId,
    val username: String,
    val email: String,
    val profilePictureUrl: String? = null,
)
