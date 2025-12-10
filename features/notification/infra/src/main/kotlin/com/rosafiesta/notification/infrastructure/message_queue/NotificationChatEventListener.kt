package com.rosafiesta.notification.infrastructure.message_queue

import com.rosafiesta.core.domain.events.chat.ChatEvent
import com.rosafiesta.core.infrastructure.message_queue.MessageQueues
import com.rosafiesta.notification.infrastructure.service.PushNotificationService
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class NotificationChatEventListener(
  private val pushNotificationService: PushNotificationService
) {

    @RabbitListener(queues = [MessageQueues.NOTIFICATION_CHAT_EVENTS])
    @Transactional
    fun handleUserEvent(event: ChatEvent){
        when(event){
            is ChatEvent.NewMessage -> {
                pushNotificationService.sendNewMessageNotification(
                    senderUsername = event.senderUsername,
                    chatId = event.chatId,
                  senderUserId = event.senderId,
                  recipientUserIds = event.recipientIds.toList(),
                  message = event.message
                )
            }
            else -> Unit
        }
    }
}