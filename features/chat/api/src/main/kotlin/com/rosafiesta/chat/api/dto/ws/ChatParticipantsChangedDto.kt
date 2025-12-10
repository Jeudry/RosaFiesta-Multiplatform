package com.rosafiesta.chat.api.dto.ws

import com.rosafiesta.core.domain.types.ChatId

data class ChatParticipantsChangedDto(
  val chatId: ChatId
)