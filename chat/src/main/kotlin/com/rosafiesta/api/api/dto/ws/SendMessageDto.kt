package com.rosafiesta.api.api.dto.ws

import com.rosafiesta.api.domain.types.ChatId
import com.rosafiesta.api.domain.types.ChatMessageId

data class SendMessageDto(
  val messageId: ChatMessageId? = null,
  val content: String,
  val chatId: ChatId? = null
)
