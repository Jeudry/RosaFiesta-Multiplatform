package com.jeudry.chat.domain.message

import com.jeudry.chat.domain.models.ChatMessage
import com.jeudry.chat.domain.models.ChatMessageDeliveryStatus
import com.jeudry.chat.domain.models.MessageWithSender
import com.jeudry.chat.domain.models.OutgoingNewMessage
import com.jeudry.core.domain.util.DataError
import com.jeudry.core.domain.util.EmptyResult
import com.jeudry.core.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface MessageRepository {
    suspend fun updateMessageDeliveryStatus(
        messageId: String,
        status: ChatMessageDeliveryStatus
    ): EmptyResult<DataError.Local>

    suspend fun fetchMessages(
        chatId: String,
        before: String? = null
    ): Result<List<ChatMessage>, DataError>

    suspend fun sendMessage(message: OutgoingNewMessage): EmptyResult<DataError>

    suspend fun retryMessage(messageId: String): EmptyResult<DataError>

    suspend fun deleteMessage(messageId: String): EmptyResult<DataError.Remote>

    fun getMessagesForChat(chatId: String): Flow<List<MessageWithSender>>
}