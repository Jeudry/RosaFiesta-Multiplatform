package com.rosafiesta.chat.domain.events

import com.rosafiesta.core.domain.types.ChatId
import com.rosafiesta.core.domain.types.UserId

data class ChatParticipantsJoinedEvent(
    val chatId: ChatId,
    val usersId: Set<UserId>
)
