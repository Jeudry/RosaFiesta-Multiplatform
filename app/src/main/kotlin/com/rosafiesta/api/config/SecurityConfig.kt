package com.rosafiesta.api.config

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity

/// <summary>
/// Security configuration to enable method-level security annotations.
/// </summary>
@Configuration
@EnableMethodSecurity
class SecurityConfig