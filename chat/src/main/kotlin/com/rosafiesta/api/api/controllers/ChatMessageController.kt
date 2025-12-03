package com.rosafiesta.api.api.controllers

import com.rosafiesta.api.api.utils.requestUserId
import com.rosafiesta.api.domain.types.ChatMessageId
import com.rosafiesta.api.services.ChatMessageService
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