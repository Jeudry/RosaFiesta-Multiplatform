package com.rosafiesta.api.user.api.dtos

import com.rosafiesta.api.user.domain.model.User

data class AuthenticatedUserDto(
    val user: UserDto,
    val accessToken: String,
    val refreshToken: String,
)
