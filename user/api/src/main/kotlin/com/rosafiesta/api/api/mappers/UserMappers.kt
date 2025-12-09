package com.rosafiesta.api.api.mappers

import com.rosafiesta.api.api.dtos.AuthenticatedUserDto
import com.rosafiesta.api.api.dtos.UserDto
import com.rosafiesta.api.domain.model.AuthenticatedUser
import com.rosafiesta.api.domain.model.User

fun AuthenticatedUser.toDto(): AuthenticatedUserDto {
    return AuthenticatedUserDto(
        user = this.user.toDto(),
        accessToken = this.accessToken,
        refreshToken = this.refreshToken
    )
}

fun User.toDto(): UserDto {
    return UserDto(
        id = this.id,
        email = this.email,
        username = this.username,
        hasEmailVerified = this.hasEmailVerified
    )
}