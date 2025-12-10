package com.rosafiesta.user.api.dtos

import com.rosafiesta.user.api.utils.Password
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.serialization.Serializable

@Serializable
data class ResetPasswordRequest @JsonCreator constructor(
    @JsonProperty("token")
    val token: String,
    @field:Password
    @JsonProperty("newPassword")
    val newPassword: String
)