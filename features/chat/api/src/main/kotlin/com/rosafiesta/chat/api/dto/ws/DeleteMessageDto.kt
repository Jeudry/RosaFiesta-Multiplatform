package com.rosafiesta.chat.api.dto.ws

import com.rosafiesta.core.domain.types.ChatId
import com.rosafiesta.core.domain.types.ChatMessageId

data class DeleteMessageDto(
  val chatId: ChatId,
  val messageId: ChatMessageId
)