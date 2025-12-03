package com.rosafiesta.api.domain.events

import com.rosafiesta.api.domain.types.ChatId
import com.rosafiesta.api.domain.types.UserId

data class ChatParticipantLeftEvent(
    val chatId: ChatId,
    val userId: UserId
)
