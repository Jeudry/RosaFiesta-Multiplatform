package com.rosafiesta.chat.api.dto.ws

import com.rosafiesta.core.domain.types.ChatId
import com.rosafiesta.core.domain.types.ChatMessageId

data class SendMessageDto(
  val messageId: ChatMessageId? = null,
  val content: String,
  val chatId: ChatId? = null
)
