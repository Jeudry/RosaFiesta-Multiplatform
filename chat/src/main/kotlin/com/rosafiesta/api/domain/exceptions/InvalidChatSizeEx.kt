package com.rosafiesta.api.domain.exceptions

class InvalidChatSizeEx: RuntimeException(
    "There must be at least two participants in a chat"
)