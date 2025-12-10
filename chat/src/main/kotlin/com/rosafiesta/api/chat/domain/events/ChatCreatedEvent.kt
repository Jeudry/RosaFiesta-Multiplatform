package com.rosafiesta.api.chat.domain.events

import com.rosafiesta.api.core.domain.types.ChatId
import com.rosafiesta.api.core.domain.types.UserId

data class ChatCreatedEvent(
  val chatId: ChatId,
  val participantIds: List<UserId>,
)