package com.rosafiesta.api.user.infrastructure.database.mappers

import com.rosafiesta.api.user.domain.model.EmailVerificationToken
import com.rosafiesta.api.user.infrastructure.database.entities.EmailVerificationTokenEntity

fun EmailVerificationTokenEntity.toModel(): EmailVerificationToken {
    return EmailVerificationToken(
        id = id!!,
        token = token,
        user = user.toModel()
    )
}