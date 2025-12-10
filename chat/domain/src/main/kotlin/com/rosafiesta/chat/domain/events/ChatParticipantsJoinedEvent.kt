package com.rosafiesta.chat.domain.events

import com.rosafiesta.api.core.domain.types.ChatId
import com.rosafiesta.api.core.domain.types.UserId

data class ChatParticipantsJoinedEvent(
    val chatId: ChatId,
    val usersId: Set<UserId>
)
