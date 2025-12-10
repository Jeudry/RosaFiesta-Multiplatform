package com.rosafiesta.user.api.dtos

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: String,
    val email: String,
    val username: String,
    val hasEmailVerified: Boolean
)