package com.rosafiesta.user.infrastructure.database.mappers

import com.rosafiesta.user.domain.model.EmailVerificationToken
import com.rosafiesta.user.infrastructure.database.entities.EmailVerificationTokenEntity

fun EmailVerificationTokenEntity.toModel(): EmailVerificationToken {
    return EmailVerificationToken(
        id = id!!,
        token = token,
        user = user.toModel()
    )
}