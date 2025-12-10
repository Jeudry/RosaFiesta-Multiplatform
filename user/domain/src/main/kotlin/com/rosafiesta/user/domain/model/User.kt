package com.rosafiesta.user.domain.model

import com.rosafiesta.api.core.domain.types.UserId

data class User(
    val id: UserId,
    val username: String,
    val email: String,
    val hasEmailVerified: Boolean,
)
