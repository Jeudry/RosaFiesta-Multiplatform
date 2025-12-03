package com.rosafiesta.api.domain.models

import com.rosafiesta.api.domain.types.UserId

data class ChatParticipant(
    val userId: UserId,
    val username: String,
    val email: String,
    val profilePictureUrl: String? = null,
)
