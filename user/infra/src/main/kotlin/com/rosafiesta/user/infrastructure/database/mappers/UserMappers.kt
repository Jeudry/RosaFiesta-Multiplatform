package com.rosafiesta.user.infrastructure.database.mappers

import com.rosafiesta.user.domain.model.User
import com.rosafiesta.user.infrastructure.database.entities.UserEntity

fun UserEntity.toModel(): User {
    return User(
        id = id!!,
        email = email,
        username = username,
        hasEmailVerified = hasVerifiedEmail
    )
}
