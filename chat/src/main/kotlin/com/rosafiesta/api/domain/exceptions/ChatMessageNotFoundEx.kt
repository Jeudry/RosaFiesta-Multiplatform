package com.rosafiesta.api.domain.exceptions

import com.rosafiesta.api.domain.types.ChatMessageId

class ChatMessageNotFoundEx(
    private val id: ChatMessageId
): RuntimeException("Chat message with id $id not found")