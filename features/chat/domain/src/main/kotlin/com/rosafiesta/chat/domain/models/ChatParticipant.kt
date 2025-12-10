package com.rosafiesta.chat.domain.models

import com.rosafiesta.core.domain.types.UserId

data class ChatParticipant(
    val userId: UserId,
    val username: String,
    val email: String,
    val profilePictureUrl: String? = null,
)
