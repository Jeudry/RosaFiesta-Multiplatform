package com.rosafiesta.user.api.dtos

import kotlinx.serialization.Serializable

@Serializable
data class AuthenticatedUserDto(
    val user: UserDto,
    val accessToken: String,
    val refreshToken: String
)