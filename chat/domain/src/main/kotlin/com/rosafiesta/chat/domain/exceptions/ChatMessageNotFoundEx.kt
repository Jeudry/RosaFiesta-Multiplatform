package com.rosafiesta.chat.domain.exceptions

import com.rosafiesta.api.core.domain.types.ChatMessageId

class ChatMessageNotFoundEx(
    private val id: ChatMessageId
): RuntimeException("Chat message with id $id not found")