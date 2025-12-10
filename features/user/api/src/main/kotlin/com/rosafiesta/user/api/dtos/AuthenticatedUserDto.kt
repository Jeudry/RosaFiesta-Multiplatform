package com.rosafiesta.user.api.dtos

import com.rosafiesta.user.domain.model.User

data class AuthenticatedUserDto(
    val user: UserDto,
    val accessToken: String,
    val refreshToken: String,
)
