package com.rosafiesta.core.api.utils

import com.rosafiesta.core.domain.exceptions.UnauthorizedEx
import com.rosafiesta.core.domain.types.UserId
import org.springframework.security.core.context.SecurityContextHolder

val requestUserId: UserId
    get() = SecurityContextHolder.getContext().authentication?.principal as? UserId
        ?: throw UnauthorizedEx()