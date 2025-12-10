package com.rosafiesta.api.chat.api.dto.ws

import com.rosafiesta.api.core.domain.types.ChatId
import com.rosafiesta.api.core.domain.types.ChatMessageId

data class DeleteMessageDto(
  val chatId: ChatId,
  val messageId: ChatMessageId
)