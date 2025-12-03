package com.rosafiesta.api.infra.configs

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "rosafiesta.web-socket")
data class WebSocketConfig(
    var allowedOrigins: List<String> = emptyList()
)