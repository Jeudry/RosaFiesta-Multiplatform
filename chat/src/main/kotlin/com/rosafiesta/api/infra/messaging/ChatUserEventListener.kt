package com.rosafiesta.api.infra.messaging

import com.rosafiesta.api.domain.events.user.UserEvent
import com.rosafiesta.api.domain.models.ChatParticipant
import com.rosafiesta.api.infra.message_queue.MessageQueues
import com.rosafiesta.api.services.ChatParticipantService
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