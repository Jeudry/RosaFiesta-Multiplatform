package com.rosafiesta.api.domain.exceptions

class InvalidTokenEx(
    override val message: String? = null,
): RuntimeException(message ?: "Invalid token")