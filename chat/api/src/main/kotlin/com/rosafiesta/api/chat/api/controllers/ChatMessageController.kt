package com.rosafiesta.api.chat.api.controllers

import com.rosafiesta.api.core.api.utils.requestUserId
import com.rosafiesta.api.core.domain.types.ChatMessageId
import com.rosafiesta.api.chat.service.ChatMessageService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/messages")
class ChatMessageController(private val chatMessageService: ChatMessageService) {
    @DeleteMapping("/{messageId}")
    fun deleteMessage(
        @PathVariable("messageId") messageId: ChatMessageId,
    ) {
        chatMessageService.deleteMessage(messageId, requestUserId)
    }


}