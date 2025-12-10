package com.rosafiesta.api.core.domain.exceptions

class InvalidTokenEx(
    override val message: String? = null,
): RuntimeException(message ?: "Invalid token")