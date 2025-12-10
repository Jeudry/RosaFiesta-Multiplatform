package com.rosafiesta.chat.api.controllers

import com.rosafiesta.chat.api.dto.AddParticipantToChatDto
import com.rosafiesta.chat.api.dto.ChatDto
import com.rosafiesta.chat.api.dto.ChatMessageDto
import com.rosafiesta.chat.api.dto.CreateChatRequest
import com.rosafiesta.chat.api.mappers.toDto
import com.rosafiesta.api.core.api.utils.requestUserId
import com.rosafiesta.api.core.domain.types.ChatId
import com.rosafiesta.chat.service.ChatMessageService
import com.rosafiesta.chat.service.ChatService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*
import java.time.Instant

@RestController
@RequestMapping("/api/chat")
class ChatController(
    private val chatService: ChatService,
    private val chatMessageService: ChatMessageService
) {
    companion object {
        private const val DEFAULT_PAGE_SIZE = 10
    }

    @GetMapping("/{chatId}")
    fun getChat(
        @PathVariable("chatId") chatId: ChatId
    ): ChatDto? {
        return chatService.getChatById(
            chatId = chatId,
            requestUserId
        )?.toDto()
    }

    @GetMapping
    fun getChatsForUser(): List<ChatDto> {
        return chatService.findChatsByUser(
            requestUserId
        ).map {
            it.toDto()
        }
    }

    @PostMapping
    fun createChat(
        @Valid @RequestBody body: CreateChatRequest
    ): ChatDto {
        return chatService.createChat(
            creatorId = requestUserId,
            otherUsersId = body.otherUsersId.toSet()
        ).toDto()
    }

    @PostMapping("/{chatId}/add")
    fun addChatParticipants(
        @PathVariable chatId: ChatId,
        @Valid @RequestBody body: AddParticipantToChatDto
    ): ChatDto {
        return chatService.addParticipantsToChat(
            chatId = chatId,
            requestUserId = requestUserId,
            userIds = body.userIds.toSet()
        ).toDto()
    }

    @DeleteMapping("/{chatId}/leave")
    fun removeChatParticipant(
        @PathVariable chatId: ChatId
    ){
        chatService.removeParticipantFromChat(
            chatId = chatId,
            userId = requestUserId
        )
    }

    @GetMapping("/{chatId}/messages")
    fun getMessagesForChat(
        @PathVariable("chatId") chatId: ChatId,
        @RequestParam("before", required = false) before: Instant = Instant.now(),
        @RequestParam("pageSize", required = false) pageSize: Int = DEFAULT_PAGE_SIZE,
    ): List<ChatMessageDto> {
        return chatService.getChatMessages(
            chatId = chatId,
            before = before,
            pageSize = pageSize
        ).map { it.toDto() }
    }
}