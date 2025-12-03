package com.rosafiesta.api.infra.database.mappers

import com.rosafiesta.api.domain.model.User
import com.rosafiesta.api.infra.database.entities.UserEntity

fun UserEntity.toModel(): User {
    return User(
        id = id!!,
        email = email,
        username = username,
        hasEmailVerified = hasVerifiedEmail
    )
}
