package com.rosafiesta.chat.domain.exceptions

import com.rosafiesta.api.core.domain.types.UserId

class ChatParticipantNotFoundEx(
    private val id: UserId
): RuntimeException("Chat participant with id $id not found")