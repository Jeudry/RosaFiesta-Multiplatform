package com.rosafiesta.api.user.domain.exception

class RateLimitEx(
    val resetsInSeconds: Long
): RuntimeException("Rate limit exceeded. Try again in $resetsInSeconds seconds")