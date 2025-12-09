package com.rosafiesta.api.domain.events.chat

import com.rosafiesta.api.domain.events.RosaFiestaEvent
import com.rosafiesta.api.domain.types.ChatId
import com.rosafiesta.api.domain.types.UserId
import java.time.Instant
import java.util.*

sealed class ChatEvent(
    override val eventId: String = UUID.randomUUID().toString(),
    override val exchange: String = ChatEventConstants.CHAT_EXCHANGE,
    override val occurredAt: Instant = Instant.now()
): RosaFiestaEvent {
    data class NewMessage(
        val senderId: UserId,
        val senderUsername: String,
        val recipientIds: Set<UserId>,
        val chatId: ChatId,
        val message: String,
        override val eventKey: String = ChatEventConstants.CHAT_NEW_MESSAGE
    ): ChatEvent(), RosaFiestaEvent


}