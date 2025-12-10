package com.rosafiesta.user.api.dtos

import com.rosafiesta.api.core.domain.types.UserId

data class UserDto(
    val id: UserId,
    val email: String,
    val username: String,
    val hasEmailVerified: Boolean,
)
