package com.rosafiesta.chat.domain.events

import com.rosafiesta.core.domain.types.ChatId
import com.rosafiesta.core.domain.types.UserId

data class ChatCreatedEvent(
  val chatId: ChatId,
  val participantIds: List<UserId>,
)