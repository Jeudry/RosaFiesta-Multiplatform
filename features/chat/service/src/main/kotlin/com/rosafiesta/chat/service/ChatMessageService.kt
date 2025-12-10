package com.rosafiesta.chat.service

import com.rosafiesta.chat.domain.events.MessageDeletedEvent
import com.rosafiesta.core.domain.events.chat.ChatEvent
import com.rosafiesta.chat.domain.exceptions.ChatMessageNotFoundEx
import com.rosafiesta.chat.domain.exceptions.ChatNotFoundEx
import com.rosafiesta.chat.domain.exceptions.ChatParticipantNotFoundEx
import com.rosafiesta.core.domain.exceptions.ForbiddenEx
import com.rosafiesta.chat.domain.models.ChatMessage
import com.rosafiesta.core.domain.types.ChatId
import com.rosafiesta.core.domain.types.ChatMessageId
import com.rosafiesta.core.domain.types.UserId
import com.rosafiesta.chat.infra.database.entities.ChatMessageEntity
import com.rosafiesta.chat.infra.database.mappers.toModel
import com.rosafiesta.chat.infra.database.repositories.ChatMessageRepository
import com.rosafiesta.chat.infra.database.repositories.ChatParticipantRepository
import com.rosafiesta.chat.infra.database.repositories.ChatRepository
import com.rosafiesta.core.infrastructure.message_queue.EventPublisher
import org.springframework.cache.annotation.CacheEvict
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class ChatMessageService(
    private val chatRepository: ChatRepository,
    private val chatParticipantRepository: ChatParticipantRepository,
    private val chatMessageRepository: ChatMessageRepository,
    private val applicationEventPublisher: ApplicationEventPublisher,
    private val eventPublisher: EventPublisher,
    private val messageCacheEvictionHelper: MessageCacheEvictionHelper
) {
    @Transactional
    @CacheEvict(
        value = ["messages"],
        key = "#chatId",
    )
    fun sendMessage(
        chatId: ChatId,
        senderId: UserId,
        content: String,
        messageId: ChatMessageId? = null,
    ): ChatMessage{
        val chat = chatRepository.findChatById(
            chatId, senderId
        ) ?: throw ChatNotFoundEx()

        val sender = chatParticipantRepository.findByIdOrNull(senderId)
            ?: throw ChatParticipantNotFoundEx(senderId)

        val savedMessage = chatMessageRepository.save(
            ChatMessageEntity(
                id = messageId ?: UUID.randomUUID(),
                chat = chat,
                sender = sender,
                content = content,
                chatId = chatId,
            )
        )

        eventPublisher.publish(
            ChatEvent.NewMessage(
                senderId = sender.userId!!,
                senderUsername = sender.username,
                recipientIds = chat.participants.map {
                    it.userId!!
                }.toSet(),
                chatId = chatId,
                message = savedMessage.content,
            )
        )

        return savedMessage.toModel()
    }

    @Transactional
    fun deleteMessage(
        messageId: ChatMessageId,
        deleterId: UserId,
    ) {
        val message = chatMessageRepository.findByIdOrNull(messageId)
            ?: throw ChatMessageNotFoundEx(messageId)

        if(message.sender!!.userId != deleterId){
            throw ForbiddenEx()
        }

        chatMessageRepository.deleteById(messageId)

        applicationEventPublisher.publishEvent(
            MessageDeletedEvent(
                chatId = message.chatId!!,
                messageId = messageId
            )
        )

        messageCacheEvictionHelper.evictMessagesCache(message.chatId!!)
    }
}