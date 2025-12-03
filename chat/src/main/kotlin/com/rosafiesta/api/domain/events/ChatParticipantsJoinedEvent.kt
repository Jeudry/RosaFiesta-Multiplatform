package com.rosafiesta.api.domain.events

import com.rosafiesta.api.domain.types.ChatId
import com.rosafiesta.api.domain.types.UserId

data class ChatParticipantsJoinedEvent(
    val chatId: ChatId,
    val usersId: Set<UserId>
)
