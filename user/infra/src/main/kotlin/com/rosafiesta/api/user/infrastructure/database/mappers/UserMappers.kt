package com.rosafiesta.api.user.infrastructure.database.mappers

import com.rosafiesta.api.user.domain.model.User
import com.rosafiesta.api.user.infrastructure.database.entities.UserEntity

fun UserEntity.toModel(): User {
    return User(
        id = id!!,
        email = email,
        username = username,
        hasEmailVerified = hasVerifiedEmail
    )
}
