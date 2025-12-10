package com.rosafiesta.user.domain.model

import com.rosafiesta.core.domain.types.UserId

data class User(
    val id: UserId,
    val username: String,
    val email: String,
    val hasEmailVerified: Boolean,
)
