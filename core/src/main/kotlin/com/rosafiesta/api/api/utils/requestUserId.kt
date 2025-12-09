package com.rosafiesta.api.api.utils

import com.rosafiesta.api.domain.exceptions.UnauthorizedEx
import com.rosafiesta.api.domain.types.UserId
import org.springframework.security.core.context.SecurityContextHolder

val requestUserId: UserId
    get() = SecurityContextHolder.getContext().authentication?.principal as? UserId
        ?: throw UnauthorizedEx()