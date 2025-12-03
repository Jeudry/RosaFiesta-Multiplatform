package com.rosafiesta.api.api.dtos

import com.rosafiesta.api.domain.types.UserId

data class UserDto(
    val id: UserId,
    val email: String,
    val username: String,
    val hasEmailVerified: Boolean,
)
