package com.jeudry.core.data.dto

import com.jeudry.core.domain.auth.User
import kotlinx.serialization.Serializable

@Serializable
data class AuthInfoSerializable(
    val accessToken: String,
    val refreshToken: String,
    val user: UserSerializable
)
