package com.rosafiesta.api.domain.events

import com.rosafiesta.api.domain.types.ChatId
import com.rosafiesta.api.domain.types.ChatMessageId

data class MessageDeletedEvent(
    val chatId: ChatId,
    val messageId: ChatMessageId
){

}