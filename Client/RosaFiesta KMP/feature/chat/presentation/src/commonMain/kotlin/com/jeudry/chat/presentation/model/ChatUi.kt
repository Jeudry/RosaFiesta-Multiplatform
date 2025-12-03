package com.jeudry.chat.presentation.model

import com.jeudry.chat.domain.models.ChatMessage
import com.jeudry.chat.domain.models.ChatParticipant
import com.jeudry.core.designsystem.components.avatar.ChatParticipantUi
import kotlin.time.Instant

data class ChatUi(
    val id: String,
    val localParticipant: ChatParticipantUi,
    val otherParticipants: List<ChatParticipantUi>,
    val lastMessage: ChatMessage?,
    val lastMessageSenderUsername: String?
)
