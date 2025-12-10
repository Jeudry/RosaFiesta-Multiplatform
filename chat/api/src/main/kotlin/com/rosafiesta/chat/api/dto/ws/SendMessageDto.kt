package com.rosafiesta.chat.api.dto.ws

import com.rosafiesta.api.core.domain.types.ChatId
import com.rosafiesta.api.core.domain.types.ChatMessageId

data class SendMessageDto(
  val messageId: ChatMessageId? = null,
  val content: String,
  val chatId: ChatId? = null
)
