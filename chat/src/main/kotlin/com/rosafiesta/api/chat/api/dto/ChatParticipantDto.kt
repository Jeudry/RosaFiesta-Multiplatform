package com.rosafiesta.api.chat.api.dto

import com.rosafiesta.api.core.domain.types.UserId

data class ChatParticipantDto(
    val id: UserId,
    val username: String,
    val email: String,
    val profilePictureUrl: String? = null,
)
