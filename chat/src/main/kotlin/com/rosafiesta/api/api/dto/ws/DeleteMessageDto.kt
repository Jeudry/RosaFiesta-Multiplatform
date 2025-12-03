package com.rosafiesta.api.api.dto.ws

import com.rosafiesta.api.domain.types.ChatId
import com.rosafiesta.api.domain.types.ChatMessageId

data class DeleteMessageDto(
  val chatId: ChatId,
  val messageId: ChatMessageId
)