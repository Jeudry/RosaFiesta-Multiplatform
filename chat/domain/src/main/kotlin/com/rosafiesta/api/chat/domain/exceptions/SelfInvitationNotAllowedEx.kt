package com.rosafiesta.api.chat.domain.exceptions

import com.rosafiesta.api.core.domain.types.UserId

class SelfInvitationNotAllowedEx(userId: UserId): RuntimeException(
    "User cannot invite themselves to a chat: $userId"
)
