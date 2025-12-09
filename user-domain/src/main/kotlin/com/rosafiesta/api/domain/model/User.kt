package com.rosafiesta.api.domain.model

import com.rosafiesta.api.domain.types.UserId

data class User(
    val id: UserId,
    val username: String,
    val email: String,
    val hasEmailVerified: Boolean,
)
