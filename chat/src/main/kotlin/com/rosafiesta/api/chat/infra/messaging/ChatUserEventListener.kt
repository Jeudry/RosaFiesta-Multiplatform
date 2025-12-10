package com.rosafiesta.api.chat.infra.messaging

import com.rosafiesta.api.core.domain.events.user.UserEvent
import com.rosafiesta.api.chat.domain.models.ChatParticipant
import com.rosafiesta.api.core.infrastructure.message_queue.MessageQueues
import com.rosafiesta.api.chat.services.ChatParticipantService
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
class ChatUserEventListener(
    private val chatParticipantService: ChatParticipantService
){
    private val logger = org.slf4j.LoggerFactory.getLogger(ChatUserEventListener::class.java)

    @RabbitListener(queues = [MessageQueues.CHAT_USER_EVENTS])
    fun handleUserEvent(event: UserEvent){
            when(event){
                is UserEvent.Created -> {
                    chatParticipantService.createChatParticipant(
                        chatParticipant = ChatParticipant(
                            userId = event.userId,
                            username = event.username,
                            email = event.email,
                            profilePictureUrl = null
                        )
                    )
                    logger.info("Chat participant created for new user: ${event.userId}")
                }
                else -> Unit
            }
    }
}