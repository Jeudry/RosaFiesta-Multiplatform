package com.jeudry.chat.data.chat

import com.jeudry.chat.data.dto.ChatDto
import com.jeudry.chat.data.dto.request.CreateChatRequest
import com.jeudry.chat.data.dto.request.ParticipantsRequest
import com.jeudry.chat.data.mappers.toDomain
import com.jeudry.chat.domain.chat.ChatService
import com.jeudry.chat.domain.models.Chat
import com.jeudry.core.data.networking.delete
import com.jeudry.core.data.networking.get
import com.jeudry.core.data.networking.post
import com.jeudry.core.domain.util.DataError
import com.jeudry.core.domain.util.EmptyResult
import com.jeudry.core.domain.util.Result
import com.jeudry.core.domain.util.asEmptyResult
import com.jeudry.core.domain.util.map
import io.ktor.client.HttpClient

class KtorChatService(
    private val httpClient: HttpClient
): ChatService {

    override suspend fun createChat(otherUserIds: List<String>): Result<Chat, DataError.Remote> {
        return httpClient.post<CreateChatRequest, ChatDto>(
            route = "/chat",
            body = CreateChatRequest(
                otherUserIds = otherUserIds
            )
        ).map { it.toDomain() }
    }

    override suspend fun getChats(): Result<List<Chat>, DataError.Remote> {
        return httpClient.get<List<ChatDto>>(
            route = "/chat"
        ).map { chatDtos ->
            chatDtos.map { it.toDomain() }
        }
    }

    override suspend fun getChatById(chatId: String): Result<Chat, DataError.Remote> {
        return httpClient.get<ChatDto>(
            route = "/chat/$chatId"
        ).map { it.toDomain() }
    }

    override suspend fun leaveChat(chatId: String): EmptyResult<DataError.Remote> {
        return httpClient.delete<Unit>(
            route = "/chat/$chatId/leave"
        ).asEmptyResult()
    }

    override suspend fun addParticipantsToChat(
        chatId: String,
        userIds: List<String>
    ): Result<Chat, DataError.Remote> {
        return httpClient.post<ParticipantsRequest, ChatDto>(
            route = "/chat/$chatId/add",
            body = ParticipantsRequest(
                userIds = userIds
            )
        ).map { it.toDomain() }
    }
}