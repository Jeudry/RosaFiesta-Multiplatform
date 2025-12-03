package com.rosafiesta.api.domain.exceptions

import com.rosafiesta.api.domain.types.UserId

class ChatParticipantNotFoundEx(
    private val id: UserId
): RuntimeException("Chat participant with id $id not found")