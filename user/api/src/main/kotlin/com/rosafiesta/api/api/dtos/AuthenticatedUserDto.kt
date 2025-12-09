package com.rosafiesta.api.api.dtos

import com.rosafiesta.api.domain.model.User

data class AuthenticatedUserDto(
    val user: UserDto,
    val accessToken: String,
    val refreshToken: String,
)
