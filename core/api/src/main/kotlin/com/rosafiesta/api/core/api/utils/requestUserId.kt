package com.rosafiesta.api.core.api.utils

import com.rosafiesta.api.core.domain.exceptions.UnauthorizedEx
import com.rosafiesta.api.core.domain.types.UserId
import org.springframework.security.core.context.SecurityContextHolder

val requestUserId: UserId
    get() = SecurityContextHolder.getContext().authentication?.principal as? UserId
        ?: throw UnauthorizedEx()