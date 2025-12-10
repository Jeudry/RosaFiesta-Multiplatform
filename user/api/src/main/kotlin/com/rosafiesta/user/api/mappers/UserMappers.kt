package com.rosafiesta.user.api.mappers

import com.rosafiesta.user.api.dtos.AuthenticatedUserDto
import com.rosafiesta.user.api.dtos.UserDto
import com.rosafiesta.api.user.domain.model.AuthenticatedUser
import com.rosafiesta.api.user.domain.model.User

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