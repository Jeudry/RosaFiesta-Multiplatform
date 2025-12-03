package com.rosafiesta.api.domain.exceptions

import com.rosafiesta.api.domain.types.UserId

class SelfInvitationNotAllowedEx(userId: UserId): RuntimeException(
    "User cannot invite themselves to a chat: $userId"
)
