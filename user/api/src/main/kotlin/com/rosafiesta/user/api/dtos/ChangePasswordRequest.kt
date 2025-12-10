package com.rosafiesta.user.api.dtos

import com.rosafiesta.user.api.utils.Password
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank
import org.jetbrains.annotations.NotNull
import kotlinx.serialization.Serializable

@Serializable
data class ChangePasswordRequest @JsonCreator constructor(
    @field:Password
    @JsonProperty("newPassword")
    val newPassword: String,
    @field:NotBlank
    @JsonProperty("oldPassword")
    val oldPassword: String
)