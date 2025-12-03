package com.rosafiesta.api.infra.database.mappers

import com.rosafiesta.api.domain.model.EmailVerificationToken
import com.rosafiesta.api.infra.database.entities.EmailVerificationTokenEntity

fun EmailVerificationTokenEntity.toModel(): EmailVerificationToken {
    return EmailVerificationToken(
        id = id!!,
        token = token,
        user = user.toModel()
    )
}