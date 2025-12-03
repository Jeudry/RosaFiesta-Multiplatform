package com.jeudry.chat.presentation.mappers

import com.jeudry.chat.domain.models.ChatParticipant
import com.jeudry.core.designsystem.components.avatar.ChatParticipantUi
import com.jeudry.core.domain.auth.User

fun ChatParticipant.toUi(): ChatParticipantUi {
    return ChatParticipantUi(
        id = userId,
        username = username,
        initials = initials,
        imageUrl = profilePictureUrl
    )
}

fun User.toUi(): ChatParticipantUi {
    return ChatParticipantUi(
        id = id,
        username = username,
        initials = username.take(2).uppercase(),
        imageUrl = profilePictureUrl
    )
}