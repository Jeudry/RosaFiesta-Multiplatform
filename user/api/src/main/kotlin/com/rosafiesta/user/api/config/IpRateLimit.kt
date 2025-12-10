package com.rosafiesta.user.api.config

import java.util.concurrent.TimeUnit

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class IpRateLimit(
    val requests: Int = 100,
    val duration: Long = 1L,
    val unit: TimeUnit = TimeUnit.HOURS
)