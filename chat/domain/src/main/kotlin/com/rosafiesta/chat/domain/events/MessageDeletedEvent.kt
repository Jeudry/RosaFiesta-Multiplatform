package com.rosafiesta.chat.domain.events

import com.rosafiesta.api.core.domain.types.ChatId
import com.rosafiesta.api.core.domain.types.ChatMessageId

data class MessageDeletedEvent(
    val chatId: ChatId,
    val messageId: ChatMessageId
){

}