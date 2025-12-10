package com.rosafiesta.api.chat.api.dto.ws

import com.rosafiesta.api.core.domain.types.ChatId

data class ChatParticipantsChangedDto(
  val chatId: ChatId
)