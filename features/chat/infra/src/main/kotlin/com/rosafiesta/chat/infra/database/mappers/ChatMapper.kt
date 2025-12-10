package com.rosafiesta.chat.infra.database.mappers

import com.rosafiesta.chat.domain.models.Chat
import com.rosafiesta.chat.domain.models.ChatMessage
import com.rosafiesta.chat.infra.database.entities.ChatEntity

fun ChatEntity.toModel(lastMessage: ChatMessage? = null): Chat {
    return Chat(
        id = id!!,
        participants = participants.map {
            it.toModel()
        }.toSet(),
        creator = creator!!.toModel(),
        lastActivityAt = lastMessage?.createdAt ?: createdAt,
        createdAt = createdAt,
        lastMessage = lastMessage
    )
}