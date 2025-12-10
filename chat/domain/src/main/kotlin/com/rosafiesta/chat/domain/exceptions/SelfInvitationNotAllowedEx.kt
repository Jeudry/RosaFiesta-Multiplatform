package com.rosafiesta.chat.domain.exceptions

import com.rosafiesta.core.domain.types.UserId

class SelfInvitationNotAllowedEx(userId: UserId): RuntimeException(
    "User cannot invite themselves to a chat: $userId"
)
