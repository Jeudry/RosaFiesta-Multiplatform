package com.rosafiesta.api.domain.exception

class RateLimitEx(
    val resetsInSeconds: Long
): RuntimeException("Rate limit exceeded. Try again in $resetsInSeconds seconds")