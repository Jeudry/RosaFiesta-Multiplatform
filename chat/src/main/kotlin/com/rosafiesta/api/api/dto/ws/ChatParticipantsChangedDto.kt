package com.rosafiesta.api.api.dto.ws

import com.rosafiesta.api.domain.types.ChatId

data class ChatParticipantsChangedDto(
  val chatId: ChatId
)