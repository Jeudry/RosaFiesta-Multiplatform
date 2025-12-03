package com.rosafiesta.api.api.dto

import com.rosafiesta.api.domain.types.UserId

data class ChatParticipantDto(
    val id: UserId,
    val username: String,
    val email: String,
    val profilePictureUrl: String? = null,
)
