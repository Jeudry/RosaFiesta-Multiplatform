package com.jeudry.chat.domain.message

import com.jeudry.chat.domain.models.ChatMessage
import com.jeudry.core.domain.util.DataError
import com.jeudry.core.domain.util.EmptyResult
import com.jeudry.core.domain.util.Result

interface ChatMessageService {
    suspend fun fetchMessages(
        chatId: String,
        before: String? = null
    ): Result<List<ChatMessage>, DataError.Remote>

    suspend fun deleteMessage(messageId: String): EmptyResult<DataError.Remote>
}